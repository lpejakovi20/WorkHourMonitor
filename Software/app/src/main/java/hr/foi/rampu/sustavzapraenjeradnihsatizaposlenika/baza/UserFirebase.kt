package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza

class UserFirebase(name:String, surname : String, email : String, password : String, role : Int, onJob:Boolean) {
    var e_name : String
    var e_surname : String
    var e_email : String
    var e_password : String
    var e_role : Int
    var e_onJob : Boolean

    init{
        e_name = name
        e_surname = surname
        e_email = email
        e_password = password
        e_role = role
        e_onJob = onJob
    }

}