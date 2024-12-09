[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/M0kyOMLZ)
# Aplikacja z mini-grami logicznymi

## Autorzy
- Jan Kuźma

## Opis
Planuję stworzyć aplikacje desktopową zawierającą kilka mini gier logicznych, jeszcze się zastanawiam nad konkretnymi przykładami, ale będzie to coś jak sudoku/krzyżówki/mastermind itp, potencjalnie jakaś przygodowa gra tekstowa.

## Funkcjonalności
- gry logiczne singleplayer z kilkoma poziomami,
- ranking graczy/historia wyników,
- pomoc dla gracza (podpowiedzi/tutoriale),
- zapis stanu gry.

## Plan
W pierwszej części planuję storzyć obsługę ekranów startowych, menu głównego, podstawową nawigacji, jakiś przykład łamigłówki.

W drugiej części planuję dodać nowe łamigłówki, rankingi/historie wyników, wskazówki dla graczy oraz potencjalnie (jeśli wyżej wymienione punkty okażą się zbyt mało czasochłonne) tryb multiplayer (być może rozgrywany na jednym telefonie, coś aka gra turowa- np kółko i krzyżyk). Dodatkowo, po konsultacji- zostanie dodany zapis stanu gry.

## Biblioteki
Będzie to apliakcja desktopowa (rezygnuję z android studio), z użyciem Compose.

# Podsumowanie części pierwszej
Struktura projektu- w głównym folderze aplikacji znajdują się następujące komponenty:
- Main.kt, który pozwala na uruchomienie aplikacji,
- DatabaseConfig.kt, który zapewnia konfigurację połączenia z bazą danych
- populateDb.kt, który umożliwia załadowania początkowych planszy sudkou do bazy danych. Aby populować bazę danych,
należy odpowiednio zmienić kod w Main.kt (uruchomić jedynie z funkcją populate())
- /backend/sudoku/Node.kt - deklaracja klasy pojedynczej komórki na planszy sudoku
- /backend/sudoku/SudokuBoard.kt - deklaracja klasy planszy sudoku, zawiera m.in serializacje oraz funckje sprawdzające poprawność planszy
- /backend/entities/SudokuBoards - zawiera deklaracje tabeli przechowującej sudoku
- /ui/screens/GameMenu.kt - główny widok statowego menu
- /ui/screens/games/MastermindScreen.kt - placeholder screen dla gry Mastermind
- /ui/screens/games/TickTakToe.kt - placeholder screen dla gry Kółko i Krzyżyk
- /ui/screens/games/SudokuScreen.kt - główny ekran dla sudoku, duża część implementacji
- /ui/sudokuComponents/NumberPad.kt - design dla klawiatury do wprowadzania numerków na plansze
- /ui/sudokuComponents/Popup.kt - design dla popup-ów, jeszcze nie używany
- /ui/sudokuComponents/SudokuBoardConfig.kt - konfiguracja kolorów dla planszy sudoku
- /ui/sudokuComponents/SudokuBoardUI.kt - design dla planszy sudoku

Aplikacja zawiera podstawową nawigacje, oraz logikę gry sudoku, wraz z przechowywaniem stanu gry w bazie danych.
Stan gry można zapisywać, chwilowo istnieje jedna plansza. Jest zaimplementowana również walidacja stanu planszy, czy
sprawdzanie wygranej gracza.

W projeckie użyłem takich bibliotek/funkcjonalności jal:
- Compose Desktop- było to wyzwanie, ponieważ nie omawialiśmy tego jeszcze na zajęciach, jednak praca z tą biblioteką okazała się bardzo przyjemna,
- Exposed – biblioteka służąca do obsługi połączeń z bazą danych w Kotlinie,
- Korutyny- użyte trochę na siłę, do komunikacji z bazą danych. Aby symulować dłuższą operację, ręcznie dodaję
sleep(1000) w funkcjach komunikujących się z bazą danych.