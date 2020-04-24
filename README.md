
# Minesweeper

Il gruppo si pone come obiettivo quello di realizzare un'applicazione orientata agli appassionati del gioco Minesweeper.

L'applicazione combinerà i classici aspetti del videogioco creato nel 1989 aggiungendo alcune novità per essere più apprezzato anche da un nuovo pubblico



##Funzionalità obbligatorie:

* Funzionamento gioco standard https://it.wikipedia.org/wiki/Campo_minato_(videogioco)#Modalit%C3%A0_di_gioco

* Calcolo e gestione del punteggio in base a difficoltà e tempo impiegato      

* Possibilità di scegliere tra tre diversi livelli di difficoltà predefiniti (Facile, Medio, Difficile, Personalizzato) 

* Gestione del campo di gioco che gestirà a sua volta le caselle che lo compongono

* Pagina con le istruzioni del gioco per i nuovi utenti 

* Nuove modalità: '1 vs 1' e 'Beat the timer', con annesso regolamento come per le modalità originale 

##Funzionalità opzionali:

* Gestione di più giocatori sulla stessa piattaforma con statistiche basate sui loro dati di gioco 

* Inserimento di effetti sonori e soundtrack per aggiungere profondità al gioco

* Personalizzazione di: effetti sonori, soundtrack ,bombe e bandierine cambiando il loro aspetto di default con immagini a piacere

##Challenge principali:

* I dati saranno salvati su file, sarà quindi necessario gestire operazioni I/O su file

* Punteggio, valore di caselle e timer dovranno essere implementati in modo da garantire un'interfaccia grafica reattiva

* Modellizzazione del campo per le varie modalità



##Suddivisione del lavoro:

Cohen: 

	- Creazione campo di gioco  GUI campo di gioco

Mengozzi:

	- GUI Menù 
	- Gestione selettore difficoltà, modalità, informazioni base

Morelli: 

	- Gestione delle singole caselle  
	- Logica di gioco 

Olivieri:

	- Gestione score system, timer e calcolo del punteggio
	- Gestione dei file utenti
