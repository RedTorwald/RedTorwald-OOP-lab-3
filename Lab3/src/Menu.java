import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class Menu {
    private static Scanner in = new Scanner(System.in);
    private static Matrix matrix=null;
    private static Text1 txt=null;
    private static String path="";
    private static String name="";


    public static void startMenu(){mainMenu();}

    public static int checkPoint(){
        Scanner scan = new Scanner(System.in);
        int redPoint;
        try{
            redPoint=Integer.parseInt(scan.nextLine());
        }
        catch(NumberFormatException e){
            System.out.println("Некорректный ввод! Введите число.");
            redPoint=checkPoint();
        }
        return redPoint;
    }
    private static void mainMenu() {
        int point =-1;
        do{
            System.out.println("+==============================+");
            System.out.println("|.........Главное меню.........|");
            System.out.println("| [1]- Показать задание   -[1] |");
            System.out.println("| [2]- Создать матрицу    -[2] |");
            System.out.println("| [3]- Ввести строку      -[3] |");
            System.out.println("| [0]- Выход из программы -[0] |");
            System.out.println("+------------------------------+");

            point=checkPoint();

            switch (point) {
                case 1:
                    System.out.println(
                    "a. найти определитель матрицы 3х3;\n" +
                    "b. найти количество слов и предложений в заданном тексте.\n" +
                    "c. найти все возможные определители второго порядка из матрицы 3х3;\n" +
                    "d. транспонировать матрицу ." + "\n");
                    break;
                case 2:
                    matrixMenu();
                    break;
                case 3:
                    textMenu();
                    break;
                case 0:
                    System.out.println("Выход из программы");
                    break;
                default:
                    System.out.println("Неверный ввод пункта!");
            }
        }while (point!=0);
    }
    private static void matrixMenu(){
        int point =-1;
        do {
            System.out.println("+================================+");
            System.out.println("|.....Меню работы с матрицей.....|");
            System.out.println("| [1]- Ввод матрицы         -[1] |");
            System.out.println("| [2]- Вывод матрицы        -[2] |");
            System.out.println("| [3]- Подсчёт детерминанта -[3] |");
            System.out.println("| [4]- Подсчёт миноров      -[4] |");
            System.out.println("| [5]- Транспонирование     -[5] |");
            System.out.println("| [6]- Запись в файл        -[6] |");
            System.out.println("| [7]- Чтение из файла      -[7] |");
            System.out.println("| [0]- Выход в главное меню -[0] |");
            System.out.println("+--------------------------------+");
            point = checkPoint();
            try{
                switch (point) {
                    case 1:
                        System.out.println("Введите количество строк");
                        int rows = Integer.parseInt(in.nextLine());
                        System.out.println("Введите количество столбцов");
                        int columns = Integer.parseInt(in.nextLine());
                        matrix = new Matrix(rows, columns);

                        if (matrix.getRows() != matrix.getColumns()) {
                            throw new Exception("Матрица не является квадратной. Повторите ввод.");
                        }
                        break;
                    case 2:
                        if (matrix==null){
                            throw new Exception("Отсутствует матрица");
                        }
                        else{
                            matrix.output();
                        }
                        break;
                    case 3:
                        if (matrix==null){
                            throw new Exception("Отсутствует матрица");
                        }
                        else{
                            System.out.println(matrix.getDeterminant());
                        }
                        break;
                    case 4:
                        if (matrix==null){
                            throw new Exception("Отсутствует матрица");
                        }
                        else{
                            matrix.getMinors();
                        }
                        break;
                    case 5:
                        if (matrix==null){
                            throw new Exception("Отсутствует матрица");
                        }
                        else{
                            matrix.transpose();
                            matrix.output();
                        }
                        break;
                    case 6:
                        if (matrix==null){
                            throw new Exception("Отсутствует матрица");
                        }
                        else{
                            inputMatrixMenu();
                        }
                        break;
                    case 7:
                        if (path.isEmpty() || name.isEmpty())
                        {
                            Scanner scanName = new Scanner(System.in);
                            System.out.println("Введите имя файла");
                            DataInputStream dis = new DataInputStream(new FileInputStream(scanName.nextLine()));
                            int r = dis.readInt();
                            if (r>100){
                                System.out.println("Недопустимое значение.");
                                return;
                            }
                            int c = dis.readInt();
                            if (c>100){
                                System.out.println("Недопустимое значение.");
                                return;
                            }
                            int[][] M;
                            try {
                                M = new int[r][c];
                            }
                            catch (Error e){
                                System.out.println("ERROR");
                                M = new int[1][1];
                            }
                            for (int i = 0; i < r; i++) {
                                for (int j = 0; j < c; j++) {
                                    M[i][j] = dis.readInt();
                                }
                            }
                            matrix = new Matrix(M);
                            matrix.output();
                        }
                        else
                        {
                            String way=path + name;
                            FileInputStream fos = new FileInputStream(way);
                            DataInputStream dis = new DataInputStream(fos);
                            int r = dis.readInt();
                            int c = dis.readInt();
                            int[][] M= new int[r][c];
                            for (int i = 0; i < r; i++) {
                                for (int j = 0; j < c; j++) {
                                    M[i][j] = dis.readInt();
                                }
                            }
                            matrix = new Matrix(M);
                            matrix.output();
                        }
                        break;
                    case 0:
                        System.out.println("Переход в главное меню");
                        break;
                    default:
                        System.out.println("Неверный ввод пункта!");
                }
            }
            catch (SecurityException e)          {System.out.println("нет доступа к редактированию");}
            catch (FileNotFoundException e)      {System.out.println("файл не найден");}
            catch (IOException e)                {System.out.println("ошибка при работе с файлом");}
            catch (NumberFormatException e)      {System.out.println("Нельзя вводить текст, повторите заново");}
            catch (NegativeArraySizeException e) {System.out.println("Нельзя вводить отрицательный размер матрицы, повторите заново");}
            catch (NoSuchElementException e)     {System.out.println("Нельзя вводить нечисловые элементы, повторите заново");}
            catch (Exception e)                  {System.out.println(e.getMessage());}
        }while (point!=0);
    }
    private static void textMenu(){
        byte[] buffer;
        int point =-1;
        do{
            System.out.println("+==========================================+");
            System.out.println("|..........Меню работы с текстом...........|");
            System.out.println("| [1]- Ввод текста                    -[1] |");
            System.out.println("| [2]- Подсчёт количества предложений -[2] |");
            System.out.println("| [3]- Подсчёт количества слов        -[3] |");
            System.out.println("| [4]- Запись в файл                  -[4] |");
            System.out.println("| [5]- Чтение строки из файла         -[5] |");
            System.out.println("| [0]- Выход в главное меню           -[0] |");
            System.out.println("+------------------------------------------+");

            point =checkPoint();
            try {
                switch (point) {
                    case 1:
                        System.out.println("ввод текста");
                        Scanner scan = new Scanner(System.in);
                        String text = scan.nextLine();
                        txt=new Text1(text);
                        break;
                    case 2:
                        if (txt ==null) {
                            throw new Exception("Отсутствует текст");
                        }
                        else
                        {
                            System.out.println("Количество предложений в тексте");
                            System.out.println(txt.getSentenceCounter());
                        }

                        break;
                    case 3:
                        if (txt ==null) {
                            throw new Exception("Отсутствует текст");
                        }
                        else
                        {
                            System.out.println("Количество слов в тексте ");
                            System.out.println(txt.getWordCounter());
                        }
                        break;
                    case 4:
                        if (txt ==null) {
                            throw new Exception("Отсутствует текст");
                        }
                        else {
                            inputMenu();
                        }
                        break;
                    case 5:
                        if (path.isEmpty() || name.isEmpty())
                        {
                            Scanner scanName = new Scanner(System.in);
                            System.out.println("Введите имя файла");
                            FileInputStream fileIn = new FileInputStream(scanName.nextLine());
                            buffer = new byte[fileIn.available()];
                            fileIn.read(buffer, 0, fileIn.available());
                            String str = new String(buffer, StandardCharsets.UTF_8);
                            txt=new Text1(str);
                            System.out.println(str);
                        }
                        else
                        {
                            String way=path + name;
                            FileInputStream fileIn = new FileInputStream(way);
                            buffer = new byte[fileIn.available()];
                            fileIn.read(buffer, 0, fileIn.available());
                            String str = new String(buffer, StandardCharsets.UTF_8);
                            txt=new Text1(str);
                            System.out.println(str);
                        }
                        break;
                    case 0:
                        System.out.println("Переход в главное меню");
                        break;
                    default:
                        System.out.println("Неверный ввод пункта!");
                }
            }
            catch (FileNotFoundException e)      {System.out.println("файл не найден / невозможно прочитать");}
            catch (NullPointerException  e)      {System.out.println("пустой файл");}
            catch (IndexOutOfBoundsException  e) {System.out.println("отрицательный размер массива");}
            catch (IOException e)                {System.out.println("ошибка при работе с файлом");}
            catch (Exception e)                  {System.out.println(e.getMessage());}
        }while (point!=0);

    }

    private static void inputMenu(){
        byte[] buffer;
        int point1 =-1;
        String defaultName="";
        System.out.println("+==========================================+");
        System.out.println("|..........Меню ввода в файл...............|");
        System.out.println("| [1]- Стандартный путь               -[1] |");
        System.out.println("| [2]- Ввод пути                      -[2] |");
        System.out.println("| [0]- Выход в главное меню           -[0] |");
        System.out.println("+------------------------------------------+");

        point1 =checkPoint();
        try {
            switch (point1) {
                case 1:
                    Scanner scanName = new Scanner(System.in);
                    System.out.println("Введите имя файла");
                    defaultName=scanName.nextLine();
                    if (defaultName.contains("*") || name.contains("?") || name.contains("<") || name.contains(">") || name.contains("/") || name.contains("\\") || name.contains(":") || name.contains("|")){
                        System.out.println("Имя содержит недопустимые символы");
                        return;
                    }
                    FileOutputStream fileOut1 = new FileOutputStream(defaultName);
                    buffer = txt.getText().getBytes();
                    fileOut1.write(buffer, 0, buffer.length);
                    fileOut1.close();
                    break;
                case 2:
                    System.out.println("Введите путь");
                    Scanner scan = new Scanner(System.in);
                    path = scan.nextLine();
                    if (path.charAt(path.length()-1)!='\\')
                    {
                        path+='\\';
                    }
                    File file = new File(path);
                    file.mkdir();
                    System.out.println("Введите имя файла");
                    name = scan.nextLine();
                    if (name.contains("*") || name.contains("?") || name.contains("<") || name.contains(">") || name.contains("/") || name.contains("\\") || name.contains(":") || name.contains("|")){
                        System.out.println("Имя содержит недопустимые символы");
                        return;
                    }
                    String way=path + name;
                    FileOutputStream fileOut = new FileOutputStream(way);
                    buffer = txt.getText().getBytes();
                    fileOut.write(buffer, 0, buffer.length);
                    fileOut.close();
                    break;
                case 0:
                    System.out.println("Переход в главное меню");
                    break;
                default:
                    System.out.println("Неверный ввод пункта!");
            }
        }
        catch (FileNotFoundException e) {System.out.println("файл не найден / невозможно прочитать");}
        catch (IOException e) {System.out.println("ошибка при работе с файлом");}
        catch (Exception e)   {System.out.println(e.getMessage());}
    }

    private static void inputMatrixMenu(){
        String defaultName="";
        int point1 =-1;
        System.out.println("+==========================================+");
        System.out.println("|..........Меню ввода в файл...............|");
        System.out.println("| [1]- Стандартный путь               -[1] |");
        System.out.println("| [2]- Ввод пути                      -[2] |");
        System.out.println("| [0]- Выход в главное меню           -[0] |");
        System.out.println("+------------------------------------------+");

        point1 =checkPoint();
        try {
            switch (point1) {
                case 1:
                    Scanner scanName = new Scanner(System.in);

                    System.out.println("Введите имя файла");
                    defaultName=scanName.nextLine();
                    if (defaultName.contains("*") || name.contains("?") || name.contains("<") || name.contains(">") || name.contains("/") || name.contains("\\") || name.contains(":") || name.contains("|")){
                        System.out.println("Имя содержит недопустимые символы");
                        return;
                    }
                    DataOutputStream dos1 = new DataOutputStream(new FileOutputStream(defaultName));
                    dos1.writeInt(matrix.getRows());
                    dos1.writeInt(matrix.getColumns());
                    for (int i = 0; i < matrix.getRows(); i++) {
                        for (int j = 0; j < matrix.getColumns(); j++) {
                            dos1.writeInt(matrix.getElement(i,j));
                        }
                    }
                    dos1.close();
                    break;
                case 2:
                    System.out.println("Введите путь");
                    Scanner scan = new Scanner(System.in);
                    path = scan.nextLine();
                    if (path.charAt(path.length()-1)!='\\')
                    {
                        path+='\\';
                    }
                    File file = new File(path);
                    file.mkdir();
                    System.out.println("Введите имя файла");
                    name = scan.nextLine();
                    if (name.contains("*") || name.contains("?") || name.contains("<") || name.contains(">") || name.contains("/") || name.contains("\\") || name.contains(":") || name.contains("|")){
                        System.out.println("Имя содержит недопустимые символы");
                        return;
                    }
                    String way=path + name;
                    DataOutputStream dos = new DataOutputStream(new FileOutputStream(way));
                    dos.writeInt(matrix.getRows());
                    dos.writeInt(matrix.getColumns());
                    for (int i = 0; i < matrix.getRows(); i++) {
                        for (int j = 0; j < matrix.getColumns(); j++) {
                            dos.writeInt(matrix.getElement(i,j));
                        }
                    }
                    dos.close();
                    break;
                case 0:
                    System.out.println("Переход в главное меню");
                    break;
                default:
                    System.out.println("Неверный ввод пункта!");
            }
        }
        catch (FileNotFoundException e) {System.out.println("файл не найден / невозможно прочитать");}
        catch (IOException e)           {System.out.println("ошибка при работе с файлом");}
        catch (Exception e)             {System.out.println(e.getMessage());}
    }
}