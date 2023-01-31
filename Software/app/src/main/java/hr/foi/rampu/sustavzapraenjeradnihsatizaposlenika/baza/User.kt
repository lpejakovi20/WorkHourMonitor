package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza

class User(name:String,surname : String,email : String, password : String, role : Int) {
    var e_name : String
    var e_surname : String
    var e_email : String
    var e_password : String
    var e_role : Int

    init{
        e_name = name
        e_surname = surname
        e_email = email
        e_password = password
        e_role = role
    }

}