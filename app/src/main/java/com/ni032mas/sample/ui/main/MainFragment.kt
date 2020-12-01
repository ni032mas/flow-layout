package com.ni032mas.sample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ni032mas.sample.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val adapter: MessageAdapter = MessageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editMessage = view.findViewById<EditText>(R.id.editMessage)
        val sendButton = view.findViewById<Button>(R.id.buttonSend)
        sendButton.setOnClickListener {
            viewModel.setMessage(editMessage.text.toString())
            editMessage.text = null
        }
        view.findViewById<RecyclerView>(R.id.recycleMessages).apply {
            layoutManager = LinearLayoutManager(requireContext()).apply { reverseLayout = true }
            adapter = this@MainFragment.adapter
        }
        viewModel.getMessages().observe(viewLifecycleOwner) { messages ->
            adapter.setMessages(messages)
        }

    }

}