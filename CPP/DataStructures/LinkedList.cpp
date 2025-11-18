#include "LinkedList.h"
#include <iostream>
using namespace std;

//Constructor for the Linked List
LinkedList::LinkedList() {

    head = nullptr;
    tail = nullptr;
}

//Deconstructor for the Linked List
LinkedList::~LinkedList() {

    Node* curr = this->head;
    while (curr != nullptr){
        Node* next = curr->next;
        delete curr;
        curr = next;
    }
}

//insertAtIndex takes two integer values and appends the reference to the value at the given index
void LinkedList::insertAtIndex(int value, int index) {

    int length = getLength();

    if(length == 0 || index == 0){
        insertAtBeginning(value);
        return;
    }
    else if(length == index) {
        insertAtEnd(value);
        return;
    }
    else if(length <= index) {
        cout << "The given index is greater than the list's length, insert failed" << endl;
    }
    else if(index < 0) {
        cout << "Please insert a positive number, insert failed" << endl;
    }
    else {
        Node* nodeAtIndex = new Node(value);
        Node* insertAfter = head;

        for(int i = 0; i < index - 1 ; i++) {
            insertAfter = insertAfter->next;
        }

        nodeAtIndex->next = insertAfter->next;
        insertAfter->next = nodeAtIndex;

        cout << "Adding value " << nodeAtIndex->value << " after value " << insertAfter->value << endl;
    }

}

//reverse reverses the linked list
void LinkedList::reverse() {

    Node* curr = this->head;
    Node* prev = nullptr;

    if (curr == nullptr) { 
        cout << "List is empty" << endl;
        return;
    }
    else if (curr->next == nullptr) { 
        cout << "List is a palindrome of length 1" << endl;
        return;
    }
    
    tail = head;
    while (curr != nullptr) {
        Node* next = curr->next;
        curr->next = prev;
        prev = curr;
        curr = next;
    }
    head = prev;

    cout << "Reversed the list" << endl;
}

//insertAtEnd takes an integer value and appends the reference to the value into a linked list
void LinkedList::insertAtEnd(int value) {

    //checks the null edge case
    if (this->head == nullptr) { 
        Node* firstNode = new Node(value);
        this->head = firstNode;
        this->tail = firstNode;
    }

    //adds the value and updates the linked list's tail
    else {
        Node* newTail = new Node(value);
        this->tail->next = newTail;
        this->tail = newTail;
    }
}

//insertAtBeginning takes an integer and prepends the reference to the value into a linked list
void LinkedList::insertAtBeginning(int value) {

    //checks the null edge case
    if (this->head == nullptr) { 
        Node* firstNode = new Node(value);
        this->head = firstNode;
        this->tail = firstNode;
    }

    //Adds the value and updates the Linked List's head
    else {
        Node* newHead = new Node(value,this->head);
        this->head = newHead;
    }
}

//The search function takes an integer parameter 'value' and iterates through the linked list
//returns a boolean true if the 'value' is present in the list, and false is the 'value' is not present
bool LinkedList::search(int value) {

    Node* curr = this->head;
    while(curr != nullptr) {
        if (curr->value == value) {
            cout << "Search " << value << ": Found" << endl;
         return true;
        }
        curr = curr->next;
    }
    cout << "Search " << value << ": Not Found" << endl;
    return false;
}

//deleteValue takes an integer value and deletes it from the linked list
//deleteValue calls helper function Search to make sure the value is in the list before proceeding
void LinkedList::deleteValue(int value){

    //check to see if value is in list
    bool notInList = !this->search(value);
    if(notInList) {
        cout << value << " is not contained in the list, can not delete" << endl;
        return;
    }

    //case - value is both list head and tail
    if(this->head->value == value && this->tail->value == value) {
        Node* deleteNode = this->head;
        this->head = nullptr;
        this->tail = nullptr;
        delete deleteNode;
        deleteNode = nullptr;
    }
    
    //case - value is list head
    else if(this->head->value == value) {
        Node* deleteNode = this->head;
        this->head = this->head->next;
        delete deleteNode;
        deleteNode = nullptr;
    }

    //case - value is list tail
    else if(this->tail->value == value) {
        Node* curr = this->head;

        while(curr->next != nullptr && curr->next->value != value) { curr = curr->next; }
        Node* deleteNode = curr->next;
        curr->next = curr->next->next;
        this->tail = curr;
        delete deleteNode;
        deleteNode = nullptr;
    }

    //else case
    else {
        Node* curr = this->head;
        while(curr->next != nullptr && curr->next->value != value) { curr = curr->next; }
        Node* deleteNode = curr->next;
        curr->next = curr->next->next;
        delete deleteNode;
        deleteNode = nullptr;
    }
}

//displayList iterates through the linked list and prints to the command line every node's value
void LinkedList::displayList() {

    //null case
    if (this->head == nullptr) {
        cout << "List: " << endl;
        return;
    }

    Node* printValue = this->head;
    cout << "List: ";
    while(printValue->next != nullptr) {
        cout << printValue->value << " -> ";
        printValue = printValue->next;
    }
    
    cout << "" << printValue->value << endl;
}

//displayListWithLength calls displayList and adds the length at the end using helper function getLength
void LinkedList::displayListWithLength(){

    displayList();
    cout << "Length of List: " << getLength() << endl;
}

//getLength obtains the length of the list and returns it
int LinkedList::getLength() {

    int length = 0;

    //null case
    if (this->head == nullptr) {
        return length;
    }

    Node* lengthCounter = this->head;
    while(lengthCounter != nullptr) {
        length++;
        lengthCounter = lengthCounter->next;
    }

    return length;
}

int main() {

    //initializes the list
    LinkedList list = LinkedList();
    
    //Test cases follwing the sample output
    list.insertAtBeginning(20);
    list.insertAtEnd(30);
    list.insertAtBeginning(10);
    list.insertAtBeginning(5);
    list.displayListWithLength();
    list.insertAtIndex(25,3);
    list.displayListWithLength();
    list.search(25);
    list.search(22);
    list.reverse();
    list.displayListWithLength();

return 0;
}
