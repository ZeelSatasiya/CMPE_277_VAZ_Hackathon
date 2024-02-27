package com.example.macroeconomicresearch.retrofit.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class EnumKey( val storageKey : String, val label : String) : Parcelable

val DebtLoadType = listOf(EnumKey("FI.RES.TOTL.MO", "Reservers"),EnumKey("FI.RES.TOTL.CD", "GNI"), EnumKey("DT.TDS.DECT.GN.ZS", "Total Debt"), EnumKey("NY.GNP.MKTP.CD", "GNI (current USD)") )
val AgricultureLoadType = listOf(EnumKey("NV.AGR.TOTL.ZS", "Contribution to GDP"),EnumKey("NV.IND.MANF.ZS", "Credit"), EnumKey("AG.CON.FERT.ZS", "Fertilizers"), EnumKey("AG.CON.FERT.PT.ZS", "Fertilizer Production") )
val MacroLoadType = listOf(EnumKey("NY.GDP.MKTP.CD", "GDP (USD)"),EnumKey("BX.KLT.DINV.WD.GD.ZS", "FDI Inflows (USD)"), EnumKey("BM.KLT.DINV.WD.GD.ZS", "FDI Outflows (USD)"), EnumKey( "API_BM.KLT.DINV.WD.GD.ZS_DS2", "Import/Export Flow"))