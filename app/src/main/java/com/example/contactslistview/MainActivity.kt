package com.example.contactslistview

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // 1. Liste des contacts
    private val contacts = mutableListOf(
        "Ahmed Ben Ali",
        "Sarra Trabelsi",
        "Mohamed Gharbi",
        "Ines Jaziri",
        "Youssef Mansouri",
        "Amira Chaabane",
        "Karim Bouazizi",
        "Nour Hammami"
    )

    // 2. Déclaration de l'adaptateur
    private lateinit var adapter: ArrayAdapter<String>

    // 3. Déclaration des vues
    private lateinit var etContact: EditText
    private lateinit var btnModifier: Button
    private lateinit var lvContacts: ListView

    // Position du contact sélectionné
    private var positionSelectionnee = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 4. Récupération des composants
        etContact = findViewById(R.id.etContact)
        btnModifier = findViewById(R.id.btnModifier)
        lvContacts = findViewById(R.id.lvContacts)

        // 5. Création de l'adaptateur
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_activated_1,
            contacts
        )

        // 6. Association de l'adaptateur à la ListView
        lvContacts.adapter = adapter

        // 7. Sélection d'un contact
        lvContacts.setOnItemClickListener { _, _, position, _ ->

            positionSelectionnee = position

            lvContacts.setItemChecked(position, true)

            etContact.setText(contacts[position])

            etContact.setSelection(etContact.text.length)
            etContact.requestFocus()
        }

        // 8. Bouton Modifier
        btnModifier.setOnClickListener {
            modifierContact()
        }

        // 9. Validation avec le clavier
        etContact.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                modifierContact()
                true
            } else {
                false
            }
        }
    }

    // 10. Fonction de modification
    private fun modifierContact() {

        // Vérifier la sélection
        if (positionSelectionnee == -1) {

            Toast.makeText(
                this,
                R.string.msg_aucune_selection,
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // Récupérer le nouveau nom
        val nouveauNom = etContact.text.toString().trim()

        // Vérifier si le champ est vide
        if (nouveauNom.isEmpty()) {

            etContact.error = getString(R.string.msg_nom_vide)

            return
        }

        // Modifier le contact
        contacts[positionSelectionnee] = nouveauNom

        // Actualiser la ListView
        adapter.notifyDataSetChanged()

        // Afficher un message
        Toast.makeText(
            this,
            "Contact modifié : $nouveauNom",
            Toast.LENGTH_SHORT
        ).show()
    }
}