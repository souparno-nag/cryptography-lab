#include <iostream>
#include <ctype.h>
#include <unordered_set>
#include <vector>
using namespace std;
 
vector<vector<char>> KeyTable (5, vector<char> (5));
 
string convertToLowercaseAndRemoveSpaces (string input) {
    int n = input.size();
    for (int i = 0; i < n; i++) {
        if (isupper(input[i])) input[i] += 32;
    }
    string output = "";
    for (int i = 0; i < n; i++) {
        if (isspace(input[i])) continue;
        output += input[i];
    }
    return output;
}
 
void createKeyTable (string input) {
    vector<char> hash;
    unordered_set<char> set;
    for (char ch : input) {
        if (ch == 'j') ch = 'i';
        if (set.find(ch) == set.end()) {
            hash.push_back(ch);
            set.insert(ch);
        }
    }
    for (char ch = 'a'; ch <= 'z'; ch++) {
        if (set.find(ch) == set.end() && ch != 'j') {
            hash.push_back(ch);
        }
    }
    int k = 0;
    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
            KeyTable[i][j] = hash[k++];
        }
    }
    cout << "Cipher Table -" << endl;
    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
            cout << KeyTable[i][j] << " ";
        }
        cout << endl;
    }
    return;
}

string normalizeInput (string input) {
    if (input.size() % 2 != 0) {
        input += 'z';
    }
    return input;
}

void search(char a, char b, vector<int>& arr) {
    if (a == 'j') a = 'i';
    if (b == 'j') b = 'i';
    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
            if (KeyTable[i][j] == a) {
                arr[0] = i; arr[1] = j;
            }
            if (KeyTable[i][j] == b) {
                arr[2] = i; arr[3] = j;
            }
        }
    }
    return;
}

string encrypt (string input) {
    int n = input.length();
    string result = "";
    for (int i = 0; i < n; i += 2) {
        vector<int> arr(4);
        search(input[i], input[i+1], arr);
        if (arr[0] == arr[2]) {
            result += KeyTable[arr[0]][(arr[1]+1)%5];
            result += KeyTable[arr[2]][(arr[3]+1)%5];
        } else if (arr[1] == arr[3]) {
            result += KeyTable[(arr[0]+1)%5][arr[1]];
            result += KeyTable[(arr[2]+1)%5][arr[3]];
        } else {
            result += KeyTable[arr[0]][arr[3]];
            result += KeyTable[arr[2]][arr[1]];
        }
    }
    return result;
}

string decrypt (string input) {
    int n = input.length();
    string result = "";
    for (int i = 0; i < n; i += 2) {
        vector<int> arr(4);
        search(input[i], input[i+1], arr);
        if (arr[0] == arr[2]) {
            result += KeyTable[arr[0]][(arr[1]+4)%5];
            result += KeyTable[arr[2]][(arr[3]+4)%5];
        } else if (arr[1] == arr[3]) {
            result += KeyTable[(arr[0]+4)%5][arr[1]];
            result += KeyTable[(arr[2]+4)%5][arr[3]];
        } else {
            result += KeyTable[arr[0]][arr[3]];
            result += KeyTable[arr[2]][arr[1]];
        }
    }
    return result;
}
 
int main() {
    string key;
    cout << "Enter the cipher key: ";
    getline(cin, key);
    key = convertToLowercaseAndRemoveSpaces(key);
    createKeyTable(key);
    string input;
    cout << "Enter the string to be encrypted: ";
    getline(cin, input);
    input = convertToLowercaseAndRemoveSpaces(input);
    input = normalizeInput(input);
    string encrypted = encrypt(input);
    cout << "Encrypted String: " << encrypted << endl;
    string decrypted = decrypt(encrypted);
    cout << "Decrypted String: " << decrypted << endl;
}