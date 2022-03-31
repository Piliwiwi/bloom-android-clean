package cl.minkai.utils.session

interface OnChangeSessionListener {
    fun onChangeUser()
    fun onRecoveryUserPassword()
    fun onReEnrollment()
    fun onReLoginUser()
}