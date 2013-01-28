#include<iostream>
#include<cstdio>
#include<vector>
#include<map>
#include<algorithm>
#include<string>
#include<cstring>
#include<cctype>
#include<stack>

using namespace std;
int main()
{
  int T;
	string S;
	int i,j,k;
	char V[101];
	cin>>T;
	cin.ignore();
	for(i=0;i<T;i++)
	{
		cin>>S;
		int size=S.size();
		k=0;
		for(j=0;j<size;j++)
		{
			if(S[j]=='(' || S[j]==')' || S[j]==':')
			{
				//V.push_back(S[j]);
				V[k]=S[j];
				//cout<<S[j];
				k++;
			}
			V[k]='\0';
		}
		S=V;
		for(j=0;j<k;j++)
			cout<<V[j]<<endl;
		for(j=0;i<strlen(V)/2;i++)
		{
			
		}
		
	}
	return 0;
}
