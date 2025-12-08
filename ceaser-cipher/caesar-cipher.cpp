#include <iostream>
#include <string>
#include <ctype.h>
using namespace std;
 
int main() {
    string input;
    cout << "Enter the text to be encrypted: "; 
    cin >> input;
    cin.ignore();

    int k;
    cout << " Enter the key for the Ceaser cypher: ";
    cin >> k;

    cout << "\nENCRYPTION" << endl;

    for (int i = 0; i < input.length(); i++) {
        if (isupper(input[i])) {
            input[i] = (char) (input[i]-'A'+k)%26 + 'A';
        } else {
            input[i] = (char) (input[i]-'a'+k)%26 + 'a';
        }
    }
    cout << "Encrypted text: " << input << endl;

    cout << "\nDECRYPTION" << endl;

    for (int i = 0; i < input.length(); i++) {
        if (isupper(input[i])) {
            input[i] = (char) (input[i]-'A'-k)%26 + 'A';
        } else {
            input[i] = (char) (input[i]-'a'-k)%26 + 'a';
        }
    }
    cout << "Decrypted text: " << input << endl;

}
