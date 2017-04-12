package TCPsimpleCalculatorST;

public class CalculiServant 
{
  int gcd(int n, int m)
  {
    int x;
    
    while(n%m!=0)
    {
      x=n%m;
      n=m;
      m=x;
    }
    return m;
  }
  
  int factorial(int n)
  {
    int i, f=1;
  
    for(i=2;i<=n;i++) f*=i;
    return f;
  }
}	
