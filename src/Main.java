public class Main {
    public static void main(String[] args) throws Exception {
        var t = "function (x, y) { 3 + 5 * 4 }";
        var t2 = "let x = 3 + 5 * 10 ^ 2; let y = x + 12;";
        var lexer = new Lexer(t2);
        var parser = new Parser(lexer);
        var interpeter = new Interpeter(parser.parse());
        System.out.println(interpeter.interpret());
    }
}
