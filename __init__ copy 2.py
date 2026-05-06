"""Main package for the app."""
import os

import pedone

# ore 10:24 pronto a bastemmiare come un cane , porco due#

class visualizzare_scacchiera:
    def __init__(self,p1,p2):
        self.size = 9

       #ho spostato la funzione che genera la mappa nel costruttore , e ho affidato il duo ipotetico valore ad una matrice 
       #che ha valore interno alla classe 


        """ implemtentata la classe del pedone , ho messo dentro di essa un
        icona per poterli differenziare , e un numero di muri a disposizione , che sara' 10 per entrambi i giocatori ,"""


        # Indica se il giocatore corrente ha scelto un'azione nel turno
        self.mossa_eseguita = False
        #chiarire l'utilita di questa funzione
        self.matrice = []
        for riga in range(self.size):
            riga_lista = []
            for col in range(self.size):
                cella = "."
                if p1.coordinate_x == riga and p1.coordinate_y == col:
                    cella = p1.icona_personaggio
                elif p2.coordinate_x == riga and p2.coordinate_y == col:
                    cella = p2.icona_personaggio
                riga_lista.append(cella)
            self.matrice.append(riga_lista)



   

    def Stampa_mappa(self):

        lettere = "abcdefghi"
        print()
        print("      " + "   ".join(lettere))
        print()
        for riga in range(self.size):
            riga_string = f"{riga + 1:>2}   "
            for col in range(self.size):
                riga_string += f"{self.matrice[riga][col]:<3} "
            print(riga_string)
        print()

        
    def cambio_turno(self):
        # Ogni nuovo turno parte senza azione eseguita
        self.mossa_eseguita = False

    def scegli_mossa(self, pedone):
        print("Scegli l'azione del turno:")
        print("1. Muovi pedone")
        print("2. Inserisci muro")
        print("3. mostra comandi")

        scelta = input("Scelta: ")[0]
        scelta = str(scelta)
        #aggiungere movimento del pedone
        if scelta == "1":
            print("Hai scelto di muovere il pedone.Premi Z per vedere i comandi ")
            mossa=input("decidi come muovere il pedone:").lower()[0]
           #La logica del movimento sarà implementata in una issue successiva.

            #controllo bonta' mossa

            if mossa == "w":
                x_stimata = pedone.coordinate_x-1
                y_stimata = pedone.coordinate_y
            elif mossa == "s":
                x_stimata = pedone.coordinate_x+1
                y_stimata = pedone.coordinate_y
            elif mossa == "a":
                x_stimata = pedone.coordinate_x
                y_stimata = pedone.coordinate_y-1
            elif mossa == "d":
                x_stimata = pedone.coordinate_x
                y_stimata = pedone.coordinate_y+1
            
            if x_stimata >=0 and x_stimata < self.size and y_stimata >=0 and y_stimata < self.size:

                if self.matrice[x_stimata][y_stimata] != ".":
                    if x_stimata+(x_stimata-pedone.coordinate_x) >=0 and x_stimata+(x_stimata-pedone.coordinate_x) < self.size and y_stimata+(y_stimata-pedone.coordinate_y) >=0 and y_stimata+(y_stimata-pedone.coordinate_y) < self.size and self.matrice[x_stimata+(x_stimata-pedone.coordinate_x)][y_stimata+(y_stimata-pedone.coordinate_y)] == ".":
                        self.matrice[x_stimata+(x_stimata-pedone.coordinate_x)][y_stimata+(y_stimata-pedone.coordinate_y)] = pedone.icona_personaggio
                        self.matrice[pedone.coordinate_x][pedone.coordinate_y] = "."
                        pedone.sposta(x_stimata+(x_stimata-pedone.coordinate_x),y_stimata+(y_stimata-pedone.coordinate_y))
                        self.mossa_eseguita = True
                    else:
                        print("Mossa non valida: c'è un ostacolo o un altro giocatore di fronte a te, e non puoi saltare.")
                        self.mossa_eseguita = False

                elif self.matrice[x_stimata][y_stimata] == ".":
                    self.matrice[pedone.coordinate_x][pedone.coordinate_y] = "."
                    pedone.sposta(x_stimata, y_stimata )
                    self.matrice[pedone.coordinate_x][pedone.coordinate_y] = pedone.icona_personaggio
                    self.mossa_eseguita = True
            else:
                print("Mossa non valida: fuori dai limiti della mappa.")
                self.mossa_eseguita = False

        elif scelta == "2":
            print("Hai scelto di inserire un muro.")
            print("La logica di inserimento muro sarà implementata in una issue successiva.")
            self.mossa_eseguita = True
        #importante laciare la riga sottostante intatta, si l'ho spostata , no non mi ingozza cambiare la frase
        elif scelta == "3":
            print("comandi: w per muovere su, s per muovere giù, a per muovere a sinistra, d per muovere a destra")
        else:
            print("Scelta non valida.")
            self.mossa_eseguita = False

    def aspetta_turno(self):
        if self.mossa_eseguita:
            input("Premi INVIO per passare al turno successivo...")
            self.cambio_turno()
        else:
            print("Non puoi passare il turno: devi prima scegliere un'azione.")

    def gestione_turno(self, pedone):

        print("giocatore corrente: " + pedone.icona_personaggio)
        comando = input(": Premi INVIO per cambiare turno oppure digita '2' per scegliere un'azione: ").lower()

    #implemetare sistemi di sicurezza contro eventuali errori di inpput 
    # ad esempio che succede se scrivo azzione??? 
        if  comando == "":
            self.aspetta_turno()
        elif comando == "2":
            while self.mossa_eseguita == False:
                self.scegli_mossa(pedone)
            self.mossa_eseguita = False
        else:
            print("Comando non valido.")


def main():
    
    p1 = pedone.Pedone(5,4,"P1")
    p2 = pedone.Pedone(6,4,"P2")
    scacchiera = visualizzare_scacchiera(p1,p2)
    
    while True:
        #passo il pedone del giocatore corrente alla funzione di gestione del turno
        
        scacchiera.Stampa_mappa()
        scacchiera.gestione_turno(p1)
        os.system("cls")
        scacchiera.Stampa_mappa()
        scacchiera.gestione_turno(p2)

# def gestisci_coincidenza(ultimo_p, penultimo_p):
    """
    if ultimo_p.coordinate_x == penultimo_p.coordinate_x and ultimo_p.coordinate_y == penultimo_p.coordinate_y:
        
 """
# faccio una funzione che deve capire  1) se due giocatori coincidono di cordinata 
# 2) chi ha fatto l'ultima mossa e sposatre l'ultimo dietro l'atro
# 3) deve capire qual'e' stata l'ultima direzione dell'ultimo giocatore

main()
