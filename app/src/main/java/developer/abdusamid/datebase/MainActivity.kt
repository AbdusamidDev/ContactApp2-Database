package developer.abdusamid.datebase

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import developer.abdusamid.datebase.adapters.ContactAdapter
import developer.abdusamid.datebase.db.MyDbHelper
import developer.abdusamid.datebase.models.Contacts
import developer.abdusamid.datebase.databinding.ActivityMainBinding
import developer.abdusamid.datebase.databinding.MyDialogBinding

class MainActivity : AppCompatActivity() {
    lateinit var contactsAdapter: ContactAdapter
    private lateinit var binding: ActivityMainBinding
    lateinit var myDBHelper: MyDbHelper
    lateinit var list: ArrayList<Contacts>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myDBHelper = MyDbHelper(this)
        list = myDBHelper.getAllContact() as ArrayList<Contacts>
        contactsAdapter = ContactAdapter(list, object : ContactAdapter.OnMyItemListener {
            override fun onClickMyItem(contacts: Contacts, position: Int, imageView: ImageView) {
                val popupMenu = PopupMenu(this@MainActivity, imageView)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item?.itemId) {
                        R.id.edit -> {
                            val dialog = AlertDialog.Builder(this@MainActivity)
                            val myDialogBinding =
                                MyDialogBinding.inflate(layoutInflater, null, false)
                            dialog.setView(myDialogBinding.root)
                            myDialogBinding.edtName.setText(contacts.name)
                            myDialogBinding.edtNumber.setText(contacts.phoneNumber)

                            dialog.setPositiveButton(
                                "Edit"
                            ) { _, _ ->
                                contacts.name = myDialogBinding.edtName.text.toString()
                                contacts.phoneNumber =
                                    myDialogBinding.edtNumber.text.toString()
                                myDBHelper.upDataContact(contacts)
                                list[position] = contacts
                                contactsAdapter.notifyItemChanged(position)
                            }
                            dialog.show()
                        }
                        R.id.delete -> {
                            myDBHelper.deleteContact(contacts)
                            list.remove(contacts)
                            contactsAdapter.notifyItemRemoved(list.size)
                            contactsAdapter.notifyItemRangeChanged(position, list.size)
                        }
                    }
                    true
                }
                popupMenu.show()
            }

        })
        binding.rv.adapter = contactsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> {
                val dialog = AlertDialog.Builder(this)
                val myDialogBinding = MyDialogBinding.inflate(layoutInflater, null, false)
                dialog.setView(myDialogBinding.root)

                dialog.setPositiveButton(
                    "Save"
                ) { _, _ ->
                    val name = myDialogBinding.edtName.text.toString()
                    val number = myDialogBinding.edtNumber.text.toString()
                    val contact = Contacts(name, number)
                    myDBHelper.addContact(contact)
                    list.add(contact)
                    contactsAdapter.notifyItemInserted(list.size)
                }

                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}