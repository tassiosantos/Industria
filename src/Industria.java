import Entidades.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// 3 – Deve conter uma classe Principal para executar as seguintes ações:
public class Industria {

    public static void main(String[] args) {
// 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.parse("2000-10-18"), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.parse("1990-05-12"), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.parse("1961-05-02"), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.parse("1988-10-14"), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.parse("1995-01-05"), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.parse("1999-11-19"), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.parse("1993-03-31"), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.parse("1994-07-08"), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.parse("2003-05-24"), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.parse("1996-09-02"), new BigDecimal("2799.93"), "Gerente"));

// 3.2 – Remover o funcionário “João” da lista.
        funcionarios.remove(1);

// 3.3 – Imprimir todos os funcionários com todas suas informações, sendo que:
//• informação de data deve ser exibido no formato dd/mm/aaaa;
//• informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.

        printAll(funcionarios);
        System.out.println("=======================================================================");
// 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        atualizarTodosSalarios(funcionarios, 10);
        printAll(funcionarios);
        System.out.println("=======================================================================");
// 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
// 3.6 – Imprimir os funcionários, agrupados por função.
        printAllByFunction(funcionarios);
        System.out.println("=======================================================================");
// 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        printByOctoberDecember(funcionarios);
// 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        System.out.println("=======================================================================");
// 3.10 – Imprimir a lista de funcionários por ordem alfabética.
        printAlphabetic(funcionarios);
        System.out.println("=======================================================================");
// 3.11 – Imprimir o total dos salários dos funcionários.
        printSumPayment(funcionarios);
        System.out.println("=======================================================================");
// 3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
        printPaymanAsMinimum(funcionarios);
        System.out.println("=======================================================================");


    }



    public static void printAll(List<Funcionario> lista){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat();

        for (Funcionario funcionaro: lista) {
            System.out.print("Nome: " + funcionaro.getNome());
            System.out.print(" Data de Nascimento: " + funcionaro.getDataNascimento().format(formatter));
            System.out.print(" Função: " + funcionaro.getFuncao());
            System.out.println(" Salário: " + decimalFormat.format(funcionaro.getSalario()));
        }
    }

    public static void printAlphabetic(List<Funcionario> funcionarios){
        List<Funcionario> alphabeticList = new ArrayList<>(funcionarios);

        Collections.sort(alphabeticList, Comparator.comparing(Funcionario::getNome));

        printAll(alphabeticList);
    }

    public static void printByOctoberDecember(List<Funcionario> funcionarios){
        List<Funcionario> octoberDecemberList = new ArrayList<>();
        for (Funcionario funcionario: funcionarios) {
            if((funcionario.getDataNascimento().getMonthValue() == 10) || (funcionario.getDataNascimento().getMonthValue() == 12)){
                octoberDecemberList.add(funcionario);
            }
        }
        System.out.println("______________________________________________________");
        printAll(octoberDecemberList);
        System.out.println("______________________________________________________");
    }

    public static void printSumPayment(List<Funcionario> funcionarios){
        BigDecimal sumPayments = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

        System.out.println("O total dos salários é:" + sumPayments);
    }


    public static void printAllByFunction(List<Funcionario> funcionarios){
        for (String funcao: mapeandoFuncoes(funcionarios)) {
            printAll(agrupandoFuncionariosFuncao(funcionarios).get(funcao));
            System.out.println("______________________________________________________");

        }
    }

    public static void printPaymanAsMinimum(List<Funcionario> funcionarios){
        for (Funcionario funcionario: funcionarios) {
            System.out.println(funcionario.getNome() + " recebe " + (funcionario.getSalario().floatValue()/1212.00) + " salário(os) mínimo(os)");
        }

    }

    public static float atualizarSalario(BigDecimal salario, float percentual){
        return (float) ((1.0 + (percentual/100))*salario.floatValue());
    }

    public static void atualizarTodosSalarios(List<Funcionario> funcionarios, float percentualAumento){
        for (Funcionario funcionario: funcionarios) {
            funcionario.setSalario(new BigDecimal(atualizarSalario(funcionario.getSalario(), percentualAumento)));
        }
    }

    public static List<String> mapeandoFuncoes(List<Funcionario> funcionarios){
        List<String> funcoes = new ArrayList<>();
        for (Funcionario funcionario: funcionarios) {
            if(!funcoes.contains(funcionario.getFuncao())){
                funcoes.add(funcionario.getFuncao());
            }
        }
        return funcoes;
    }

    public static HashMap<String, List<Funcionario>> agrupandoFuncionariosFuncao(List<Funcionario> funcionarios){
        Map<String, List<Funcionario>> mapFuncaoFuncionarios = new HashMap<String, List<Funcionario> >();
        for (Funcionario funcionario: funcionarios) {
            if(mapFuncaoFuncionarios.containsKey(funcionario.getFuncao())){
                mapFuncaoFuncionarios.get(funcionario.getFuncao()).add(funcionario);
            }else{
                mapFuncaoFuncionarios.put(funcionario.getFuncao(), new ArrayList<Funcionario>(){{add(funcionario);}});
            }
        }


        return (HashMap<String, List<Funcionario>>) mapFuncaoFuncionarios;
    }






}
