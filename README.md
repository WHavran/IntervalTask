# Interval Task – konzolová aplikace (externí zadání problému)
Drobná ukázka implementace vlastního řešení v rámci OPP.

## Popis
Konzolová aplikace řeší úlohu pokrytí přímky minimálním počtem úseček.  
Na vstupu dostane množinu úseček definovaných počátečním a koncovým bodem.  
Na výstupu vrátí podmnožinu úseček, která:
1. sjednocením pokryje maximální možnou část přímky,
2. obsahuje minimální počet úseček nutných k zachování tohoto maximálního pokrytí.

## Architektura
- **Line** – datová struktura reprezentující úsečku (interval).
- **LineCollector** – uchovává všechny vstupní úsečky.
- **OperationManager** – obsahuje business logiku aplikace (validace vstupů, vyhodnocovací algoritmus).
- **LifeCycleManager** – zajišťuje základní běh konzolové aplikace (singleton).

## Vstupní formát
Každý řádek obsahuje dvojici hodnot: <start_usecky> <konec_usecky>. Ukončení vstupu je prázdný řádek.

## Příklad
### Vstup:
1 5 /
1 2 /
2 6 /
4 7 /
8 10

### Výstup:
1 5 /
4 7 /
8 10
