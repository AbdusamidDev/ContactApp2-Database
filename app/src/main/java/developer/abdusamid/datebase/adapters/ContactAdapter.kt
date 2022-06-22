package developer.abdusamid.datebase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import developer.abdusamid.datebase.models.Contacts
import developer.abdusamid.datebase.databinding.ItemContactBinding

class ContactAdapter(private val list: List<Contacts>, var onMyItemListener: OnMyItemListener) :
    RecyclerView.Adapter<ContactAdapter.VH>() {
    inner class VH(var itemContactBinding: ItemContactBinding) :
        RecyclerView.ViewHolder(itemContactBinding.root) {
        fun onBind(contacts: Contacts, position: Int) {
            itemContactBinding.txtName.text = contacts.name
            itemContactBinding.txtNumber.text = contacts.phoneNumber
            itemContactBinding.imageMore.setOnClickListener {
                onMyItemListener.onClickMyItem(contacts, position, itemContactBinding.imageMore)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnMyItemListener {
        fun onClickMyItem(contacts: Contacts, position: Int, imageView: ImageView)
    }
}