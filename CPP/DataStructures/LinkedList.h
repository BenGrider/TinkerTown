#ifndef LINKEDLIST_H
#define LINKEDLIST_H

struct Node{
    int value;
    Node* next;
    
    Node(int v){
        value = v;
        next = nullptr;
    }
    Node(int v, Node* n){
        value = v;
        next = n;
    }
};

class LinkedList {

    public:

        LinkedList();
        ~LinkedList();
        void insertAtEnd(int value);
        void insertAtIndex(int value, int index);
        void insertAtBeginning(int value);
        void deleteValue(int value);
        void displayList();
        void displayListWithLength();
        void reverse();
        bool search(int value);
        int getLength();


    private:

        Node* head;
        Node* tail;
};

#endif