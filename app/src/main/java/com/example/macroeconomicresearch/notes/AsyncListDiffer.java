package com.example.macroeconomicresearch.notes;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

@SuppressLint("RestrictedApi")
public class AsyncListDiffer<T> {
   private final ListUpdateCallback mUpdateCallback;
   @SuppressWarnings("WeakerAccess") /* synthetic access */
   final AsyncDifferConfig<T> mConfig;
   private Executor mMainThreadExecutor;

   private static class MainThreadExecutor implements Executor {
       final Handler mHandler = new Handler(Looper.getMainLooper());
       MainThreadExecutor() {}
       @Override
       public void execute(@NonNull Runnable command) {
           mHandler.post(command);
       }
   }

   // TODO: use MainThreadExecutor from supportlib once one exists
   private static final Executor sMainThreadExecutor = new MainThreadExecutor();

   public interface ListListener<T> {

       void onCurrentListChanged(@NonNull List<T> previousList, @NonNull List<T> currentList);
   }

   private final List<ListListener<T>> mListeners = new CopyOnWriteArrayList<>();


   public AsyncListDiffer(@NonNull RecyclerView.Adapter adapter,
                          @NonNull DiffUtil.ItemCallback<T> diffCallback) {
       this(new AdapterListUpdateCallback(adapter),
               new AsyncDifferConfig.Builder<>(diffCallback).build());
   }


   @SuppressWarnings("WeakerAccess")
   public AsyncListDiffer(@NonNull ListUpdateCallback listUpdateCallback,
                          @NonNull AsyncDifferConfig<T> config) {
       mUpdateCallback = listUpdateCallback;
       mConfig = config;
       if (config.getMainThreadExecutor() != null) {
           mMainThreadExecutor = config.getMainThreadExecutor();
       } else {
           mMainThreadExecutor = sMainThreadExecutor;
       }
   }

   @Nullable
   private List<T> mList;

   @NonNull
   private List<T> mReadOnlyList = Collections.emptyList();

   @SuppressWarnings("WeakerAccess") /* synthetic access */
           int mMaxScheduledGeneration;


   @NonNull
   public List<T> getCurrentList() {
       return mReadOnlyList;
   }


   public void submitList(@Nullable final List<T> newList) {
       submitList(newList, null);
   }

   public void justSetList(@Nullable final List<T> newList)
   {
       if(newList == mList)
       {
           return;
       }
       if (newList == null || newList.isEmpty()) {
           mList = null;
           mReadOnlyList = Collections.emptyList();
           return;
       }

           mList = newList;
           mReadOnlyList = Collections.unmodifiableList(newList);

   }



   @SuppressWarnings("WeakerAccess")
   public void submitList(@Nullable final List<T> newList,
                          @Nullable final Runnable commitCallback) {
       // incrementing generation means any currently-running diffs are discarded when they finish
       final int runGeneration = ++mMaxScheduledGeneration;
       if (newList == mList) {
           // nothing to do (Note - still had to inc generation, since may have ongoing work)
           if (commitCallback != null) {
               commitCallback.run();
           }
           return;
       }

       final List<T> previousList = mReadOnlyList;

       // fast simple remove all
       if (newList == null) {
           int countRemoved = mList.size();
           mList = null;
           mReadOnlyList = Collections.emptyList();
           // notify last, after list is updated
           mUpdateCallback.onRemoved(0, countRemoved);
           onCurrentListChanged(previousList, commitCallback);
           return;
       }

       // fast simple first insert
       if (mList == null) {
           mList = newList;
           mReadOnlyList = Collections.unmodifiableList(newList);
           // notify last, after list is updated
           mUpdateCallback.onInserted(0, newList.size());
           onCurrentListChanged(previousList, commitCallback);
           return;
       }

       final List<T> oldList = mList;
       mConfig.getBackgroundThreadExecutor().execute(() -> {
           final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
               @Override
               public int getOldListSize() {
                   return oldList.size();
               }

               @Override
               public int getNewListSize() {
                   return newList.size();
               }

               @Override
               public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                   T oldItem = oldList.get(oldItemPosition);
                   T newItem = newList.get(newItemPosition);
                   if (oldItem != null && newItem != null) {
                       return mConfig.getDiffCallback().areItemsTheSame(oldItem, newItem);
                   }
                   // If both items are null we consider them the same.
                   return oldItem == null && newItem == null;
               }

               @Override
               public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                   T oldItem = oldList.get(oldItemPosition);
                   T newItem = newList.get(newItemPosition);
                   if (oldItem != null && newItem != null) {
                       return mConfig.getDiffCallback().areContentsTheSame(oldItem, newItem);
                   }
                   if (oldItem == null && newItem == null) {
                       return true;
                   }
                   // There is an implementation bug if we reach this point. Per the docs, this
                   // method should only be invoked when areItemsTheSame returns true. That
                   // only occurs when both items are non-null or both are null and both of
                   // those cases are handled above.
                   throw new AssertionError();
               }

               @Nullable
               @Override
               public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                   T oldItem = oldList.get(oldItemPosition);
                   T newItem = newList.get(newItemPosition);
                   if (oldItem != null && newItem != null) {
                       return mConfig.getDiffCallback().getChangePayload(oldItem, newItem);
                   }

                   throw new AssertionError();
               }
           });

           mMainThreadExecutor.execute(() -> {
               if (mMaxScheduledGeneration == runGeneration) {
                   latchList(newList, result, commitCallback);
               }
           });
       });
   }

   @SuppressWarnings("WeakerAccess") /* synthetic access */
   void latchList(
           @NonNull List<T> newList,
           @NonNull DiffUtil.DiffResult diffResult,
           @Nullable Runnable commitCallback) {
       final List<T> previousList = mReadOnlyList;
       mList = newList;
       // notify last, after list is updated
       mReadOnlyList = Collections.unmodifiableList(newList);
       diffResult.dispatchUpdatesTo(mUpdateCallback);
       onCurrentListChanged(previousList, commitCallback);
   }

   private void onCurrentListChanged(@NonNull List<T> previousList,
                                     @Nullable Runnable commitCallback) {
       // current list is always mReadOnlyList
       for (ListListener<T> listener : mListeners) {
           listener.onCurrentListChanged(previousList, mReadOnlyList);
       }
       if (commitCallback != null) {
           commitCallback.run();
       }
   }


   public void addListListener(@NonNull ListListener<T> listener) {
       mListeners.add(listener);
   }

   public void removeListListener(@NonNull ListListener<T> listener) {
       mListeners.remove(listener);
   }
}
