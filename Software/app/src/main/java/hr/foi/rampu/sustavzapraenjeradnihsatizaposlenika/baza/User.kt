package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza

class User(name:String,surname : String,email : String, password : String) {
    lateinit var e_name : String
    lateinit var e_surname : String
    lateinit var e_email : String
    lateinit var e_password : String

    init{
        e_name = name
        e_surname = surname
        e_email = email
        e_password = password

    }

}