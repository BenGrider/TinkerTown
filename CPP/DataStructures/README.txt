Singly Linked List with added Functions

Ben Grider - bwg0061 - bengrider@my.unt.edu - 9/15/25

-----------------------------------------------------------------------------------------------

LinkedList.cpp and LinkedList.h are a part of Lab 3 of CSCE2110.501

This program is an implementation of the singly linked list data structure written in c++.

-----------------------------------------------------------------------------------------------

HOW TO RUN 

g++ LinkedList.cpp -o run
./run

-----------------------------------------------------------------------------------------------

Implemented functions

insertAtIndex:
    insertAtIndex takes two integer values and appends the reference to the value at the given index
insertAtEnd:
    insertAtEnd takes an integer value and appends the reference to the value 
    into a linked list
insertAtBeginning:
    insertAtBeginning takes an integer and prepends the reference to the value 
    into a linked list
search:
    The search function takes an integer parameter 'value' and iterates through 
    the linked list returns a boolean true if the 'value' is present in the list, 
    and false is the 'value' is not present
deleteValue:
    deleteValue takes an integer value and deletes it from the linked list
    deleteValue calls helper function Search to make sure the value is in the
    list before proceeding
displayList:
    displayList iterates through the linked list and prints to the command line every node's value
displayListWithLength:
    displayListWithLength calls displayList and adds the length at the end using helper function getLength
getLength:
    getLength obtains the length of the list and returns it
reverse:
    reverse reverses the linked list
main:
    initializes list
    tests cases