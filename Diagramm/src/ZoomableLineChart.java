

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.layout.LayoutInfo;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.part.NumberAxis;
import javafx.scene.layout.Resizable;
import javafx.geometry.BoundingBox;
import javafx.scene.layout.LayoutInfoBase;
import javafx.scene.layout.Priority;
import javafx.scene.control.Slider;
import javafx.scene.layout.Container.*;
import javafx.scene.layout.Panel;
import java.util.Date;
import nl.tikal.finance.transaction.util.ChartUtil;

/**
 * @author André Hogenkamp
 *
 */
public class ZoomableLineChart extends CustomNode, Resizable {
    public var title:String;
    public var xAxis:NumberAxis;
    public var yAxis:NumberAxis;
    public var xAxisLabel:String;
    public var yAxisLabel:String;
    public var lowerboundFixed:Number;
    public var formatTickLabel:function (val:Float):String;

// LineChart stuff
    // variable that holds the dates of the transactiondata.
    var dates:Date[];
    var tickUnit = bind (endSliderValue - beginSliderValue)/10;
    // variable that makes tickunits dependent on difference between upperbound and lowerbound.
    var yAxisTickUnit = 1;
    var lowerbound = 0.0; //lowest value of data
    var upperbound = 0.0; //highest value of data.

    var dataSubset:LineChart.Data[];
    public var data:LineChart.Series[] on replace {
            dataSubset = data[0].data[0 ..  data[0].data.size()];
        };

    var beginSliderValue:Number = 0 on replace {
        if (beginSliderValue > endSlider.value) {endSlider.value} else {beginSlider.value}
        dataSubset = data[0].data[beginSliderValue.intValue() ..  endSliderValue.intValue()];
        calculateBounds(dataSubset);
        // hack to redraw LineChart.
        var seriesData:LineChart.Data = dataSubset[0];
        delete dataSubset[0];
        insert seriesData before dataSubset[0];
    }

    var endSliderValue:Number = data[0].data.size() on replace {
        if (endSliderValue < beginSlider.value) {beginSlider.value} else {endSlider.value}
        dataSubset = data[0].data[beginSliderValue.intValue() ..  endSliderValue.intValue()];
        calculateBounds(dataSubset);
        // hack to redraw LineChart.
        var seriesData:LineChart.Data = dataSubset[0];
        delete dataSubset[0];
        insert seriesData before dataSubset[0];
    }

    // recalculate tickUnit for y-axis and upperbound and lowerbound for selected subset.
    function calculateBounds(dataSubset : LineChart.Data[]):Void {
        upperbound = Number.NEGATIVE_INFINITY;
        lowerbound = Number.POSITIVE_INFINITY;
        for (dataItem:LineChart.Data in dataSubset) {
            if (dataItem.yValue < lowerbound) {
                lowerbound = dataItem.yValue;
            }
            if (dataItem.yValue > upperbound) {
                upperbound = dataItem.yValue;
            }
        }
        yAxisTickUnit = ChartUtil.calculateFloorPowerOfTen(upperbound.intValue() - lowerbound.intValue());
        upperbound = ChartUtil.calculateUpperbound(upperbound, yAxisTickUnit);
        lowerbound = ChartUtil.calculateLowerbound(lowerbound, yAxisTickUnit);
    }

    var lineChart = LineChart {
        id: "lineChart"
        title: title
        showSymbols: false;
        plotBackgroundFill: Color.AQUA
        xAxis: NumberAxis {
            tickUnit: bind tickUnit
            lowerBound: bind beginSliderValue
            upperBound: bind endSliderValue
            label: xAxisLabel
            formatTickLabel: formatTickLabel
        }

        yAxis: NumberAxis {
            tickUnit: bind yAxisTickUnit
            lowerBound: bind lowerbound
            upperBound: bind upperbound
            label: yAxisLabel
        }
        data: [
//            for (i in [0 .. data.size() -1]) {
                LineChart.Series {
                    name: bind data[0].name
                    fill: bind data[0].fill
                    data: bind dataSubset
                }
//            }
        ]
    }

    var beginSlider = Slider {
        id: "beginSlider"
        blockIncrement: 10.0
        majorTickUnit: 50
        minorTickCount: 1
        snapToTicks: true
        showTickMarks: true
        min: 0
        max: bind 468;//dataSubset.size()
        value: bind beginSliderValue with inverse
    }

    var endSlider = Slider {
        id: "endSlider"
        blockIncrement: 10.0
        majorTickUnit: 50
        minorTickCount: 1
        snapToTicks: true
        showTickMarks: true
        min: 0
        max: 468;//bind dataSubset.size()
        value: bind endSliderValue with inverse
    }

// Panel stuff

    var panel:Panel = Panel {
        var bottomHeight:Number;
        width: bind width
        height: bind height
        content: [
            lineChart,
            beginSlider,
            endSlider
        ]
        onLayout: function():Void {
            bottomHeight = 0;
            for (node in getManaged(panel.content)) {
                if (node.id == "beginSlider") {
                    bottomHeight = bottomHeight + getNodePrefHeight(node);
                    setNodeWidth(node, (panel.width - 100)/2);
                    positionNode(node, 20, panel.height);
                }
                if (node.id == "endSlider") {
                    setNodeWidth(node, (panel.width - 100)/2);
                    positionNode(node, panel.width/2, panel.height);
                }
                if (node.id == "lineChart") {
                    setNodeWidth(node, panel.width - 50);
                    setNodeHeight(node, panel.height - bottomHeight - 50);
                    positionNode(node, 50, 32);
                }
            }
        }
        prefWidth: function(height:Number):Number {
            return panel.width;
        }
        prefHeight: function(width:Number):Number {
            return panel.height;
        }

    };

    override function getPrefWidth(height:Number):Number {
        // compute preferred width based on own content/state
        // may query preferred widths of children during computation
        var prefWidth = lineChart.width;
        return prefWidth;
    }
    override function getPrefHeight(width:Number):Number {
        // compute preferred height based on won content/state
        // may query preferred heights of children during computation
        var prefHeight = lineChart.height + beginSlider.height + endSlider.height;
        return prefHeight;
    }

    override var width on replace {
        requestLayout();
    }

    override var height on replace {
        requestLayout();
    }

    override var layoutInfo: LayoutInfoBase = LayoutInfo {
        hfill: true vfill: true
        hgrow: Priority.ALWAYS vgrow: Priority.ALWAYS
    }

    // ensure layoutBounds tracks current width/height
    override var layoutBounds = bind BoundingBox {
        minX: 0
        minY: 0
        width: this.width
        height: this.height
    }

    override var children = bind panel;

}
