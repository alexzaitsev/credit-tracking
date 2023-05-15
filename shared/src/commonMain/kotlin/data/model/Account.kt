package data.model

data class Account(
    val bankName: String,
    val personName: String,
    val balance: Double,
    val lastTxs: List<Tx>
)
