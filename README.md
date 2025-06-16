1. Contesto di riferimento e obiettivi

Contesto 
Si vuole realizzare un sistema informativo su Web che offre informazioni relative a libri. Oltre agli utenti occasionale, due tipologie di attori interagiscono con il sistema: gli utenti registrati e l'amministratore. Un utente registrato, oltre a poter consultare le informazioni, possono aggiungere recensioni ai libri (ogni utente registrato può aggiungere al più una recensione, ogni libro può avere più recensioni, al più una per ogni utente).

L'amministratore può svolgere le seguenti operazioni: possono aggiungere, modificare, cancellare libri e autori. Gli amministratori possono anche cancellare le recensioni (ma non possono modificarle).

Per ogni libro sono di interesse il titolo, l'anno di pubblicazione, una o più immagini, l'autore o gli autori (ipotizziamo ogni libro possa avere anche più di un autore). Per gli autori sono di interesse il nome, cognome, la data di nascita e l'eventuale data di morte, la nazionalità, una fotografia.
Ai libri possono essere associate recensioni scritte da utenti registrati. Una recensione ha un titolo, un voto (un intero compresso tra 1 e 5) e un testo.

2. Obiettivi

L'obbiettivo è creare un sistema informativo su Web che contempli i seguenti casi d'uso che seguono: