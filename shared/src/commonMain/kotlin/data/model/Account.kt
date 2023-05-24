package data.model

data class Account(
    val id: String,
    val bankName: String,
    val personName: String,
    val balance: Double,
    val lastTxs: List<Tx>
)
