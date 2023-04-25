data class AccountInfo(
    val bankName: String,
    val balance: Float,
    val status: String,
    val lastTxs: List<Transaction>
)
