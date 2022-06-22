package developer.abdusamid.datebase.utils

import developer.abdusamid.datebase.models.Contacts

interface DBServiceInterface {
    fun addContact(contacts: Contacts)
    fun deleteContact(contacts: Contacts)
    fun upDataContact(contacts: Contacts): Int
    fun getAllContact(): List<Contacts>
}