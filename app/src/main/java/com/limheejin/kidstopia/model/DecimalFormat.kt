package com.limheejin.kidstopia.model

import java.text.DecimalFormat

fun formatDecimal(number: Int): String = DecimalFormat("###,###,###.##").format(number)
