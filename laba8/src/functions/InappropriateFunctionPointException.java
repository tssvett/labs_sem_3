package functions;

public class InappropriateFunctionPointException extends Exception {
    public InappropriateFunctionPointException(String message){
        super("[!]" + message);
    }
    public InappropriateFunctionPointException(){
        super();
    }

    public InappropriateFunctionPointException(int index){
        super("[!] Ошибка по индексу" + index + "!" + " Координата x задаваемой точки лежит вне интервала, определяемого значениями соседних точек табулированной функции.");
    }

    public InappropriateFunctionPointException(Throwable cause){
        super(cause);
    }
}
