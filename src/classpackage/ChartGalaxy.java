/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.TextAnchor;

public class ChartGalaxy {

    //ultimo paramentro para transparencia.. quanto menor mais transparente
    private static final Color transparent = new Color(0, 0, 250, 55);

    public static XYDataset createDataset() {
        LabeledXYDataset series = new LabeledXYDataset();
        List<String> vetorLinha = new ArrayList<>();
        List<List> lista = new ArrayList<>();
        try {
            FileReader arq = new FileReader("Arquivo.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            while (linha != null) {
                String caracteres = " #@_\\/*|";
                String parts[] = linha.split("[" + Pattern.quote(caracteres) + "]");
                for (String i : parts) {
                    vetorLinha.add(i);
                }
                lista.add(vetorLinha);
                vetorLinha = new ArrayList<>();
                linha = lerArq.readLine();
            }
             
            for (int i = 0; i < lista.size(); i++) {
               // for (int j = 0; j < vetorLinha.size(); j++) {
                    double a = Double.parseDouble(lista.get(i).get(0).toString());
                    double b = Double.parseDouble(lista.get(i).get(1).toString());
                    String label =lista.get(i).get(2).toString();
                   //System.out.println(lista.get(i).get(j));
                //}
                series.add(a, b,label);
            }
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return series;
    }

    private static class LabeledXYDataset extends AbstractXYDataset {

        private static final int N = 100;
        private List<Number> x = new ArrayList<Number>(N);
        private List<Number> y = new ArrayList<Number>(N);
        private List<String> label = new ArrayList<String>(N);

        public void add(double x, double y, String label) {
            this.x.add(x);
            this.y.add(y);
            this.label.add(label);
        }

        public String getLabel(int series, int item) {
            return label.get(item);
        }

        @Override
        public int getSeriesCount() {
            return 1;
        }

        @Override
        public Comparable getSeriesKey(int series) {
            return "Documentos";
        }

        @Override
        public int getItemCount(int series) {
            return label.size();
        }

        @Override
        public Number getX(int series, int item) {
            return x.get(item);
        }

        @Override
        public Number getY(int series, int item) {
            return y.get(item);
        }
    }

    private static class LabelGenerator implements XYItemLabelGenerator {

        @Override
        public String generateLabel(XYDataset dataset, int series, int item) {
            LabeledXYDataset labelSource = (LabeledXYDataset) dataset;
            return labelSource.getLabel(series, item);
        }

    }

    private static JFreeChart createChart(final XYDataset dataset) {
        JFreeChart jfreechart = ChartFactory.createScatterPlot(
                "MDS Galaxy", "X", "Y", createDataset(),
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setBaseItemLabelGenerator(new LabelGenerator());
        renderer.setBaseItemLabelPaint(Color.WHITE);//label
        renderer.setBasePositiveItemLabelPosition(
                new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER));
        renderer.setBaseItemLabelFont(
                renderer.getBaseItemLabelFont().deriveFont(15f));
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());

        //set false para linhas no grafico
        xyPlot.setDomainGridlinesVisible(false);
        xyPlot.setRangeGridlinesVisible(false);
        xyPlot.setRangeMinorGridlinesVisible(false);
        xyPlot.setRangeCrosshairVisible(false);
        xyPlot.setRangeCrosshairLockedOnData(false);
        xyPlot.setRangeZeroBaselineVisible(false);
        xyPlot.setBackgroundPaint(Color.BLACK);
        double size = 40.0;
        double delta = size / 2.0;
        Shape shape = new Rectangle2D.Double(-delta, -delta, size, size);

        renderer.setSeriesShape(0, shape);
        renderer.setSeriesPaint(0, transparent);

        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setRange(-0.1, 0.1);
        domain.setTickUnit(new NumberTickUnit(0.1));
        domain.setVerticalTickLabels(true);
        NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
        range.setRange(-0.1, 0.1);
        range.setTickUnit(new NumberTickUnit(0.1));

        return jfreechart;
    }

 /*   public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1000, 500);
            }
        };
        f.add(chartPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }*/

    public ChartGalaxy() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1000, 500);
            }
        };
        f.add(chartPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}
