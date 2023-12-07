package com.sabanci.ovatify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sabanci.ovatify.databinding.FriendFlowBinding
import com.sabanci.ovatify.fragment.AddFriendFragment
import com.sabanci.ovatify.fragment.FriendsFragment
import com.sabanci.ovatify.fragment.IncomingFragment
import com.sabanci.ovatify.fragment.ManualSongUploadFragment
import com.sabanci.ovatify.fragment.OutgoingFragment

class FriendFlowActivity:AppCompatActivity() {
    private lateinit var binding:FriendFlowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FriendFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var isFriendButtonClicked=true
        var isAddButtonClicked=false
        var isIncomingButtonClicked=false
        var isOutgoingButtonClicked=false
        binding.friendButton.setOnClickListener{
            if (!isFriendButtonClicked) {
                isFriendButtonClicked = true
                isAddButtonClicked = false
                isIncomingButtonClicked=false
                isOutgoingButtonClicked=false
                binding.friendButton.setBackgroundResource(R.drawable.upload_open)
                binding.addButton.setBackgroundResource(R.drawable.upload_closed)
                binding.incomingButton.setBackgroundResource(R.drawable.upload_closed)
                binding.outgoingButton.setBackgroundResource(R.drawable.upload_closed)
                replaceFragment(FriendsFragment())
            }
        }
        binding.addButton.setOnClickListener{
            if (!isAddButtonClicked) {
                isFriendButtonClicked = false
                isAddButtonClicked = true
                isIncomingButtonClicked=false
                isOutgoingButtonClicked=false
                binding.friendButton.setBackgroundResource(R.drawable.upload_closed_reverse)
                binding.addButton.setBackgroundResource(R.drawable.upload_open_reverse)
                binding.incomingButton.setBackgroundResource(R.drawable.upload_closed_reverse)
                binding.outgoingButton.setBackgroundResource(R.drawable.upload_closed_reverse)
                replaceFragment(AddFriendFragment())
            }
        }
        binding.incomingButton.setOnClickListener{
            if (!isIncomingButtonClicked) {
                isFriendButtonClicked = false
                isAddButtonClicked = false
                isIncomingButtonClicked=true
                isOutgoingButtonClicked=false
                binding.friendButton.setBackgroundResource(R.drawable.upload_closed_reverse)
                binding.addButton.setBackgroundResource(R.drawable.upload_closed_reverse)
                binding.incomingButton.setBackgroundResource(R.drawable.upload_open_reverse)
                binding.outgoingButton.setBackgroundResource(R.drawable.upload_closed_reverse)
                replaceFragment(IncomingFragment())
            }
        }
        binding.outgoingButton.setOnClickListener{
            if (!isOutgoingButtonClicked) {
                isFriendButtonClicked = false
                isAddButtonClicked = false
                isIncomingButtonClicked=false
                isOutgoingButtonClicked=true
                binding.friendButton.setBackgroundResource(R.drawable.upload_closed_reverse)
                binding.addButton.setBackgroundResource(R.drawable.upload_closed_reverse)
                binding.incomingButton.setBackgroundResource(R.drawable.upload_closed_reverse)
                binding.outgoingButton.setBackgroundResource(R.drawable.upload_open_reverse)
                replaceFragment(OutgoingFragment())
            }
        }
        binding.backButton.setOnClickListener{
            onBackPressed()
        }

    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentmanager= supportFragmentManager
        fragmentmanager.beginTransaction().replace(R.id.fragmentContainerViewUpload,fragment).commit()
    }
}