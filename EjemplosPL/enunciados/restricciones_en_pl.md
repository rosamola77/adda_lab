En programación lineal entera podemos introducir el operador **allDifferents**:

$$
AD_{i=0}^{n-1} x_i \equiv
\left\{ 
\begin{array}{ll}
z_{ij} = 1 \rightarrow x_i - x_j \ge 1 & i \in [0,n), j \in [0,n) | j \gt i \\
z_{ij} = 0 \rightarrow x_i - x_j \le -1 & i \in [0,n), j \in [0,n) | j \gt i \\
bin \ z_{ij} & i \in [0,n), j \in [0,n) | j \gt i  \\
\end{array}
\right.
$$


Toma valores en el conjunto:

$$
x \in S_{i=0}^{n-1} v_i \equiv 
\left\\{ 
\begin{array}{ll}
x = \sum\limits_{i=0}^{n-1} z_i v_i & i \in [0,n)\\
\sum\limits_{i=0}^{n-1} z_i = 1 & \\
bin \ z_i & i \in [0,n)
\end{array}
\right.
$$

Máximo de un conjunto de valores

$$
x = \max_{i=0..n-1} v_i \equiv 
\left\\{ 
\begin{array}{ll}
\min z & \\
z \ge v_i & i \in [0,n)\\
\end{array}
\right.
$$

Mínimo de un conjunto de valores

$$
x = \min_{i=0..n-1} v_i \equiv 
\left\\{ 
\begin{array}{ll}
\max z & \\
z \le v_i & i \in [0,n)\\
\end{array}
\right.
$$

Permutaciones de u conjunto de valores:

$$
P_{i=0}^{n-1} (x_i,v_i) \equiv 
\left\\{ 
\begin{array}{ll}
x_i = \sum\limits_{j=0}^{n-1} z_{ij} v_i & i \in [0,n)\\
\sum\limits_{j=0}^{n-1} z_{ij} = 1 & i \in [0,n) \\
\sum\limits_{i=0}^{n-1} z_{ij} = 1 & j \in [0,n) \\
bin \ z_{ij} & i \in [0,n), j \in [0,n)
\end{array}
\right.
$$

Valor absoluto

$$
x = |y| \equiv 
\left\\{ 
\begin{array}{ll}
\min z & \\
z \ge y & \\
z \ge -y & \\
\end{array}
\right.
$$

Desigualdad

$$
x \neq y \equiv 
\left\\{ 
\begin{array}{ll}
z = 1 \rightarrow x - y \ge 1 &  \\
z = 0 \rightarrow x - y \le -1 &  \\
bin \ z  & \\ 
\end{array}
\right.
$$