set V;
set E, within V cross V;

param n;
param c;

param p{i in 0..n-1};
param w{i in 0..n-1};

var Chosen{i in 0..n-1}, binary;

s.t. weightRestriction: sum{i in 0..n-1} Chosen[i]*w[i] <= c;
s.t. conflictGraph{(i,j) in E}: Chosen[i] + Chosen[j] <= 1;

maximize z: sum{i in 0..n-1} Chosen[i]*p[i];

solve;
end;
