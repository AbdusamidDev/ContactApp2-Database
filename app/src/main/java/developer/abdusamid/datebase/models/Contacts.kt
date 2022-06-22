package developer.abdusamid.datebase.models

class Contacts {
    var id: Int? = null
    var name: String? = null
    var phoneNumber: String? = null

    constructor(name: String?, phoneNumber: String?) {
        this.name = name
        this.phoneNumber = phoneNumber
    }

    constructor(id: Int?) {
        this.id = id
    }

    constructor(id: Int?, name: String?, phoneNumber: String?) {
        this.id = id
        this.name = name
        this.phoneNumber = phoneNumber
    }
}