package com.koog.example.mock_data

import com.koog.example.model.Contact

val contactList = listOf(
    Contact(id = 100, name = "Alice", lastName = "Smith", phone = "+1 415 555 1234"),
    Contact(id = 101, name = "Bob", lastName = "Johnson", phone = "+49 151 23456789"),
    Contact(id = 102, name = "Charlie", lastName = "Williams", phone = "+36 20 123 4567"),
    Contact(id = 103, name = "Daniel", lastName = "Anderson", phone = "+46 70 123 45 67"),
    Contact(id = 104, name = "Daniel", lastName = "Garcia", phone = "+34 612 345 678"),
)

val contactMap = contactList.associateBy { it.id }