int main(int a, int b){	//main function
	int x;
	int y;
	int result;
	while(a < b){
		x = a;
		a = a + 1;
	}
	y = 5;
	if(x < y){
		result = x;
	}else{
		result = y;
	}
    return result;
}