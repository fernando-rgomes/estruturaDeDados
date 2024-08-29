package br.ufpb.dcx.GfelixG.EstruturaDeDados;

import java.util.ListIterator;

public class Deque <Item> implements Iterable<Item>{

    private int n; //Contador de elementos
    private No Sentinela; //Nó artificial para marcar inicio e fim

    public Deque(){
        n = 0;
        Sentinela = new No();
        Sentinela.prox = Sentinela;
        Sentinela.ant = Sentinela;
    }

    private class No{ //Classe Nó
        private Item dado;
        private No prox;
        private No ant;
    }

    public void push_front(Item item){
        //criar novo nó e armazenar dados
        No tmp = new No();
        tmp.dado = item;

        //definir anterior e proximo do novo nó
        tmp.ant = Sentinela;
        tmp.prox = Sentinela.prox;

        //ajustar a sentinela e o anterior
        Sentinela.prox = tmp;
        tmp.prox.ant = tmp;
        ++n;
    }

    public void push_back(Item item){
        //criar novo nó e armazenar dados
        No tmp = new No();
        tmp.dado = item;

        //definir anterior e proximo do novo nó
        tmp.ant = Sentinela.ant;
        tmp.prox = Sentinela;

        //ajustar a sentinela e o anterior
        Sentinela.ant = tmp;
        tmp.ant.prox = tmp;
        n++;
    }

    public Item pop_front(){
        No tmp = Sentinela.prox;
        Item meuDado = tmp.dado;
        //atualizar o nó anterior para apontar o próximo do que será removido
        tmp.ant.prox = tmp.prox;
        //atualizar o nó próximo para apontar para o anterior do que será removido
        tmp.prox.ant = tmp.ant;
        --n;
        return meuDado;
    }

    public Item pop_back(){
        No tmp = Sentinela.ant;
        Item meuDado = tmp.dado;
        //atualizar o nó anterior para apontar o próximo do que será removido
        tmp.ant.prox = tmp.prox;
        //atualizar o nó próximo para apontar para o anterior do que será removido
        tmp.prox.ant = tmp.ant;
        --n;
        return meuDado;
    }
    public No first(){
        if(Sentinela == Sentinela.prox) return null;
        return Sentinela.prox;
    }

    public boolean isEmpty(){return n==0;}//Sentinela == Sentinela.prox
    public int sizer(){return n;}

    public ListIterator<Item> iterator(){
        return new DequeIterator();
    }

    public class DequeIterator implements ListIterator<Item>{
        private No atual = Sentinela.prox;
        private int indice = 0;
        private No acessaUltimo = null;

        public boolean hasNext(){return indice < n;}
        public boolean hasPrevious(){return indice > 0;}
        public int previousIndex(){return indice - 1;}
        public int nextIndex(){return indice;}

        public Item next(){
            if(!hasNext()) return null;

            Item meuDado = atual.dado;
            acessaUltimo = atual;
            atual = atual.prox;
            indice++;
            return meuDado;
        }

        public Item previous(){
            if(!hasPrevious()) return null;

            Item meuDado = atual.dado;
            acessaUltimo = atual;
            atual = atual.ant;
            indice--;
            return meuDado;
        }

        public Item get(){
            if(atual == null) throw new IllegalStateException();
            return atual.dado;
        }

        public void set(Item x){
            if(acessaUltimo == null) throw new IllegalStateException();
            acessaUltimo.dado = x;
        }

        public void remove(){
            if(acessaUltimo == null) throw new IllegalStateException();
            acessaUltimo.ant.prox = acessaUltimo.prox;
            acessaUltimo.prox.ant = acessaUltimo.ant;
            --n;
            if (atual == acessaUltimo)
                atual = acessaUltimo.prox;
            else
                indice--;
            acessaUltimo = null;
        }

        public void add(Item x){
            //Inserir após atual
            No tmp = new No();
            tmp.dado = x;

            tmp.prox = atual.prox;
            tmp.ant = atual;

            tmp.prox.ant = tmp;
            atual.prox = tmp;
            n++;
        }
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    public static void main(String[]args){
        int n = Integer.parseInt(args[0]);

        //add elements 1, ..., n
        StdOut.println(n + " random integers between 0 and 99");
        Deque<Integer> list = new Deque<Integer>();
        for(int i = 0; i < n; i++){
            list.push_front(i);
        }
        StdOut.println(list);
        StdOut.println();
        while(!list.isEmpty()){
            StdOut.println(list.pop_front());
        }
        for(int i = 0; i < n; i++){
            list.push_back(i);
        }
        StdOut.println(list);
        StdOut.println();

        ListIterator<Integer> it = list.iterator();
        while (it.hasNext()){
            int x = it.next();
            it.set( x + 1);
        }

        StdOut.println(list);
        StdOut.println();
        while(it.hasPrevious()){
            int x = it.previous();
            it.set( x + x + x);
        }
        StdOut.println(list);
        StdOut.println();
        while(it.hasNext()){
            int x = it.next();
            if(x%2 == 0) it.remove();
        }

        StdOut.println(list);
        StdOut.println();
        while(it.hasPrevious()){
            int x = it.previous();
            it.add(x + x);
        }
        StdOut.println(list);
        StdOut.println();

    }

}
