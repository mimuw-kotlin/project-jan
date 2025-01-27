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

# Jak uruchomić aplikację?
Można wywołać polecenie ./gradlew run. Ważna notatka- w funkcji main w pliku Main.kt jest wywoływana funckja populate().
Czyści ona lokalnie bazę danych, oraz insertuje do niej 2 plansze sudoku. Jeżeli chcemy, aby stan planszy zapisywał się
między wywołaniami aplikacji- po pierwszym uruchomieniu należy usunąć (wykomentować) tą linijkę.

# Cześć druga:
- Naprawić błędy z PR:
  - dwa razy wpisane dependencje
  - stare wersje bibliotek
  - Enum Game -> Screen
  - selectedNumber można przypisać do jakiegoś vala i wtedy po sprawdzeniu != null nie trzeba będzie robić !!
  - przypadek że board jest nullem (SudokuBoard? -> SudokuBoard)
  - Pair<SudokuBoard, Boolean> -> lepiej dataclass z nazwanymi polami
  - podświetlanie liczb edytowanych przez użytkownika (rozróżnienie ich z liczbami początkowymi)
- Licznik czasu (możliwość pauzy, może w trakcie pauzy być widoczny ranking)
- Generator planszy (algorytmy grafowe)
- Notatki
- Unit Test (np dla generowania sudoku)
- UI Test

# Finalny opis funckjonalności z cz.2:
- Naprawiono wszystkie błędy z części 1,
- Licznik czasu:
  - Czas liczony w milisekudnach, często updateowany (za pomocą korutyn),
  - Możliwośc pauzy,
  - W trackie pauzy zamiast planszy wyświetla się ranking dla aktualnej gry,
  - Czas zapisuje się wraz z planszą, oraz zeruje w odpowiednich przypadkach: gdy gra się skończy czy gdy zaczniemy nową grę,
  - Gdy plansza zostanie ukończona, aktualny czas zapisuje się do rankingu,
- Dodana nowa plansza, oraz możliwość przełączania między planszami (jeśli czas był wcześniej zapisany, to po wczytaniu planszy również się pojawi)
- Funkcjonalność notatek- możliwość wejścia w tryb edycji notatek,
- Jeśli włączony jest tryb edycji- kliknięcie na przycisk sprawia, że zapisze się notatka a nie liczba. 
- Ostatecznie jako że bardziej rozbudowałem funkcjonalność czasu i rankingu (rozszerzenie o drugą grę, nowy model w bazie danych, wyświetlanie rankingu - zajęło to sporo czasu + testy) to zrezygnowałem z generatora plansz

# Podsumowanie dodanych plików podczas cz.2:
- /backend/sudoku/CellCoordinates , BoardStatus, BoardWithTime - wszystkie 3 klasy powstały za wskazówką, aby nie uzywać nienazwanych Par, a data class
- /backend/database/entities/Rankings - zawiera deklaracje tabeli przechowującej rankingi- dla każdej gry osobny ranking
- /backend/database/services/RankingService - pomocnicze funkcje dodające/getujące ranking
- /ui/screens/games/SudokuScreen.kt - (tu zaszło dużo zmian, nie jest to dodany plik)- wydzieliłem inne pomniejsze pliki i umieściłem je w sudokuComponents, oraz dodawałem nowe funckjonalnośc
- /ui/sudokuComponents/Popup.kt - plik został usunięty
- /ui/sudokuComponents/DisplayTime.kt - używany w kilku miejscach komponent ładnie formatujący czas z milisekund
- /ui/sudokuComponents/Menu.kt - zawiera przyciski ulokowane na dole SudokuScreen
- /ui/sudokuComponents/Timer.kt - wyświetlanie timera, przycisk do pauzy jest ulokowany w Menu.kt
- oraz inne zmodyfikowane pliki, w mniejszym bądź większym stopniu.

# Testy:
- proste testy dla backendu, usuwanie dodawnaie sudoku/rankingów
- proste testy dla frontendu, testowanie edytowania planszy oraz dodawania notatek