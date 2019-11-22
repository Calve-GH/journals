todo list:
1) hibernate second lvl cache ref overview;
2) refactoring;
3) Exceptions review;
4) Add email reg;
5) Add outgoing p2;

Optional spring gen util;
Optional format excel download;

-рефакторинг index gen импорт
-в тестовом варианте не стоит констр на исходящих на дату отправки и индекс;
-bug in outgoing import if previous cell is null than iteration makes two steps forward;
(ex: if genIndex in excel table is null);