# Trabalho de Computação Evolutiva
Problema da Mochila com Grafo de Conflito - Algoritmo Genético

## Estrutura do projeto

Todas as instâncias de problemas (os itens e o grafo de conflito) estão na
pasta `./data`.

O arquivo com a implementação da solução em GLPK está na pasta `./glpk`.

## Como executar

Para rodar a solução GLPK, basta rodar um comando no terminal na pasta raiz do
projeto seguindo este formato:

    glpsol -m ./glpk/formulation.mod -d ./data/C10_BPPC_5_0_5.txt_0.1.dat -o solução.txt --tmlim 3600

Lembrando de alterar o arquivo `C10_BPPC_5_0_5.txt_0.1.dat` para o arquivo
correspondente a instância desejada do problema e o arquivo `solução.txt` para
o nome desejado do arquivo em que a solução do problema será salva.

Para compilar e rodar a implementação é necessário ter o JDK instalado:

    http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

O código da implementação está dentro da pasta `./implementation/src/`.

Para compilar a aplicação basta navegar até a pasta `./implementation/src/` e
executar o seguinte comando:

    javac MainApplication.java

Após compilar, arquivos do tipo .class serão criados dentro da pasta
`./implementation/src/` e podem ser executados.

Para executar o programa, basta navaegar até a pasta `./implementation/src/` e
executar um comando no seguinte modelo:

    java MainApplication 1 20 30 1000 < ../../data/C10_BPPC_5_0_5.txt_0.1.dat

Onde os parâmetros são:

1. Semente de aleatoriedade (neste caso 1)
2. Tamanho das populações (neste caso 20)
3. Probabilidade de mutaçao (neste caso 30)
4. Número de iterações sem melhoras (neste caso 1000)

A entrada padrão é o arquivo da instância desejada. No exemplo é
`../../data/C10_BPPC_5_0_5.txt_0.1.dat`.
