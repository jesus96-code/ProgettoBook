1. Contesto di riferimento e obiettivi

Contesto 
Si vuole realizzare un sistema informativo su Web che offre informazioni relative a libri. Oltre agli utenti occasionale, due tipologie di attori interagiscono con il sistema: gli utenti registrati e l'amministratore. Un utente registrato, oltre a poter consultare le informazioni, possono aggiungere recensioni ai libri (ogni utente registrato può aggiungere al più una recensione, ogni libro può avere più recensioni, al più una per ogni utente).

L'amministratore può svolgere le seguenti operazioni: possono aggiungere, modificare, cancellare libri e autori. Gli amministratori possono anche cancellare le recensioni (ma non possono modificarle).

Per ogni libro sono di interesse il titolo, l'anno di pubblicazione, una o più immagini, l'autore o gli autori (ipotizziamo ogni libro possa avere anche più di un autore). Per gli autori sono di interesse il nome, cognome, la data di nascita e l'eventuale data di morte, la nazionalità, una fotografia.
Ai libri possono essere associate recensioni scritte da utenti registrati. Una recensione ha un titolo, un voto (un intero compresso tra 1 e 5) e un testo.

2. Obiettivi

L'obbiettivo è creare un sistema informativo su Web che contempli i seguenti casi d'uso che seguono:

3.Casi d'uso 

Caso d'uso UC1: Consulta informazioni  
Attore primario: utente occasionale 
Un qualunque accesso anonimo (che può offrire direttamente il portale senza essere necessariamente registrato) può consultare tutte le informazioni sui libri (incluse le recensioni) e sugli autori, ma non possono aportare nessuna tipo di modifica ai dati.

Scenario principale di successo: 
Il sistema mostra i dettagli della richiesta visualizzando la pagina. L'utente ripete i passi precedente un numero indefinito di volte.

Caso d'uso UC2: Inserisci nuovo libro
Attore primario: l'amministratore
Si presuppone che l'utente principale sia quello registrato con appositi permessi di 'amministrazione', registrato con apposito ruolo su DB in grado di effettuare operazioni di inserimento.

Scenario principale di successo: E' altresì necessario ovviamente loggarsi al sistema con le proprie credenziali che verranno riconosciute dal sistema come utenza amministrazione. L'amministratore (o amministrazione) crea un libro dall'apposita voce di menu, successivamente imposta un titolo, anno di pubblicazione e una o più immagini.

Caso d'uso UC3: Inserisci nuova recensione
Attore principale: utente registrato
Si presuppone che l'utente principale sia quello registrato con appositi permessi di "utente registrato", registrato con un apposito ruolo su DB in grado di effettuare operazioni di recensioni a quel libro.

Scenario principale di successo: L'utente registrato consulta l'elenco dei libri, attraverso il menu. Il sistema mostra all'utente registrato l'elenco dei libri. L'utente seleziona un libro, il sistema mostra il libro nel dettaglio e l'utente ha la possibilità di aggiungere uno o più recensioni.

Caso d'uso UC4: Aggiornamento libro
Attore primario: l'amministratore
Si presuppone che l'utente principale sia quello registrato con appositi permessi di 'amministrazione', registrato con apposito ruolo su DB in grado di effettuare operazioni di aggiornamenti.

Scenario principale di successo: E' altresì necessario ovviamente loggarsi al sistema con le proprie credenziali che verranno riconosciute dal sistema come utenza amministrazione. L'amministratore (o amministrazione) aggiorna un libro dall'apposita voce di menu, successivamente può aggiornare il titolo, l'anno di uscita e potrebbe anche aggiornare gli autori del libro.

Caso d'uso UC5: Contatti
Attore primario: utente occasionale

Scenario principale di successo: L'utente seleziona i contatti del sito web. Il sistema mostra la form e l'utente inserisce il proprio nome, email e richieste. Il sistema inoltra tutto all'indirizzo email del sito.

Caso d'uso UC6: Chi siamo
Attore primario: utente occasionale

Scenario principale di successo: L'utente seleziona le informazioni del sito e il sistema mostra la pagina con le informazioni.



