package data.model

data class Account(
    val bankName: String,
    val personName: String,
    val balance: Float,
    val lastTxs: List<Tx>
)
