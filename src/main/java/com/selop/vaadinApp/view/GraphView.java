package com.selop.vaadinApp.view;

import at.downdrown.vaadinaddons.highchartsapi.HighChart;
import at.downdrown.vaadinaddons.highchartsapi.HighChartFactory;
import at.downdrown.vaadinaddons.highchartsapi.exceptions.HighChartsException;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartConfiguration;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartType;
import at.downdrown.vaadinaddons.highchartsapi.model.data.PieChartData;
import at.downdrown.vaadinaddons.highchartsapi.model.series.PieChartSeries;
import com.selop.vaadinApp.data.ExpensesService;
import com.selop.vaadinApp.domain.Expense;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Example for a graph viz done with the HighChart API.
 * Implemented as ViewScopedView.
 *
 * @author selop
 */
@SpringView(name = GraphView.VIEW_NAME)
public class GraphView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "graph";

    public static final String GRAPH_NAME = "Expenses Distribution";

    @Autowired
    private GraphGreeter graphGreeter;

    ExpensesService service = ExpensesService.getInstance();

    @PostConstruct
    void init() {
        setMargin(true);
        setSpacing(true);
        refreshData();
        addComponent(new Label(graphGreeter.sayHello()));
    }

    /**
     * Using the HighChart API to create a pie chart.
     * Reading the data set created by the ExpensesService.
     */
    private void refreshData() {
        ChartConfiguration pieConfiguration = new ChartConfiguration();
        pieConfiguration.setTitle(GRAPH_NAME);

        PieChartSeries chartData = new PieChartSeries(GRAPH_NAME);
        pieConfiguration.getSeriesList().add(chartData);
        pieConfiguration.setChartType(ChartType.PIE);

        // Using the stream API to sort all expense entries by their category
        Map<String, List<Expense>> expensesByCategory = service.findAll("").stream().collect(
                Collectors.groupingBy(Expense::getCategory)
        );

        // For the the now sorted lists of entries, we want to count the amount spent for each category
        expensesByCategory.values().stream().filter(c -> c.size() > 1).forEach(
                l -> countCategorySum(l, chartData)
        );

        try {
            HighChart pieChart = HighChartFactory.renderChart(pieConfiguration);
            pieChart.setHeight(40, Unit.PERCENTAGE);
            pieChart.setWidth(100, Unit.PERCENTAGE);
            this.addComponent(pieChart);
            this.setComponentAlignment(pieChart, Alignment.MIDDLE_CENTER);
        } catch (HighChartsException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper func to calculate the sum spent on each category.
     * Using the stream API in combination with the reduce func.
     *
     * @param list Number of lists for each category of expenses
     * @param series The chartConfig member to include data sets for the visualization
     */
    private void countCategorySum(List<Expense> list, PieChartSeries series) {
        Integer categorySum = list.stream().reduce(
                0,
                (sum, p) -> sum += p.getExpenseValue(),
                (sum1, sum2) -> sum1 + sum2
        );
        series.getData().add(new PieChartData(list.get(0).getCategory(), categorySum));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }
}
