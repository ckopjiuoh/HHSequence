# HHSequence

#Бесконечная последовательность

Возьмём бесконечную цифровую последовательность, образованную склеиванием последовательных положительных чисел: S = 123456789101112131415...
Определите первое вхождение заданной подпоследовательности A в бесконечной последовательности S (нумерация начинается с 1).

Программа должна читать данные из stdin и выводить ответы в stdout.

Пример входных данных (по одной подпоследовательности на строку, максимальная длина подпоследовательности — 50 символов):

6789

111

Пример выходных данных:

6

12


Решение должно быть выполнено в виде законченной программы на языке Java или Python.
Обратите внимание, что программа должна читать данные из stdin и выводить ответ в stdout в описанном в условии формате.
Очень важно не просто решить задачу, но и найти наиболее оптимальное решение!

#Решение

Подход "в лоб" не подходит к данной задаче, из-за очень долгого времени поиска.
За основу был взят подход разложения числа на все возможные последовательности:

123 :

1 2 3

12 23

123

После этого проводятся проверки каждого из этих чисел, может ли оно использоваться как число, индекс которого и нужно найти.
В 123 таким числом является 1.

Далее находится индекс смещения, потому что искомое число может входи в подпоследовательность не целиком.

После этого вычисляется индекс.

Команда для запуска программы:

#java -jar HHSequence.jar

Программа принимает значения, пока нне завершен ввод. Завершением ввода является ввод пустой строки.

Длина подпоследовательности ограничена 50 символами по условиям задачи. 
Но проверки показали, что программа может вычислять и строки длинной более 200 символов без потери производительности.
