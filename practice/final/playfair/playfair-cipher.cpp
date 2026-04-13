#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;

char keyTable[5][5];

void generateKeyMatrix(string key)
{
	vector<char> uniqueChars;

	for (char ch : key)
	{
		if (ch == 'J')
			ch = 'I';
		if (find(uniqueChars.begin(), uniqueChars.end(), ch) == uniqueChars.end())
			uniqueChars.push_back(ch);
	}

	for (char ch = 'A'; ch <= 'Z'; ch++)
	{
		if (ch != 'J' && find(uniqueChars.begin(), uniqueChars.end(), ch) == uniqueChars.end())
			uniqueChars.push_back(ch);
	}

	int k = 0;
	for (int i = 0; i < 5; i++)
	{
		for (int j = 0; j < 5; j++)
		{
			keyTable[i][j] = uniqueChars[k++];
			cout << keyTable[i][j] << " ";
		}
		cout << endl;
	}
}

vector<int> search(char a, char b)
{
	if (a == 'J')
		a = 'I';
	if (b == 'J')
		b = 'I';
	if (a == b)
		b = 'X';
	vector<int> arr(4);
	for (int i = 0; i < 5; i++)
	{
		for (int j = 0; j < 5; j++)
		{
			if (keyTable[i][j] == a)
			{
				arr[0] = i;
				arr[1] = j;
			}
			if (keyTable[i][j] == b)
			{
				arr[2] = i;
				arr[3] = j;
			}
		}
	}
	return arr;
}

string encrypt(string plaintext)
{
	if (plaintext.length() % 2 != 0)
		plaintext += 'Z';
	string ciphertext = "";
	for (int i = 0; i < plaintext.length(); i += 2)
	{
		vector<int> arr = search(plaintext[i], plaintext[i + 1]);
		if (arr[0] == arr[2])
		{
			ciphertext += keyTable[arr[0]][(arr[1] + 1) % 5];
			ciphertext += keyTable[arr[2]][(arr[3] + 1) % 5];
		}
		else if (arr[1] == arr[3])
		{
			ciphertext += keyTable[(arr[0] + 1) % 5][arr[1]];
			ciphertext += keyTable[(arr[2] + 1) % 5][arr[3]];
		}
		else
		{
			ciphertext += keyTable[arr[0]][arr[3]];
			ciphertext += keyTable[arr[2]][arr[1]];
		}
	}
	return ciphertext;
}

string decrypt(string ciphertext)
{
	string plaintext = "";
	for (int i = 0; i < ciphertext.length(); i += 2)
	{
		vector<int> arr = search(ciphertext[i], ciphertext[i + 1]);
		if (arr[0] == arr[2])
		{
			plaintext += keyTable[arr[0]][(arr[1] + 4) % 5];
			plaintext += keyTable[arr[2]][(arr[3] + 4) % 5];
		}
		else if (arr[1] == arr[3])
		{
			plaintext += keyTable[(arr[0] + 4) % 5][arr[1]];
			plaintext += keyTable[(arr[2] + 4) % 5][arr[3]];
		}
		else
		{
			plaintext += keyTable[arr[0]][arr[3]];
			plaintext += keyTable[arr[2]][arr[1]];
		}
	}
	return plaintext;
}

int main()
{
	string key = "HELLO";
	string message = "WEAREGOINGTOTHECIRCUS";

	generateKeyMatrix(key);

	string ciphertext = encrypt(message);
	string plaintext = decrypt(ciphertext);

	cout << ciphertext << endl;
	cout << plaintext << endl;
}