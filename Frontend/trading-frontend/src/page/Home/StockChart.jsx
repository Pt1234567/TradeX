import { Button } from '@/components/ui/button';
import { OpacityIcon } from '@radix-ui/react-icons';
import React, { useState } from 'react'
import ReactApexChart from 'react-apexcharts';


const timeSeries=[

       {
            keyword:"DIGITAL_CURRENCY_DAILY",
            Key:"Time Series (Daily)",
            lable:"1 Day",
            value:1,
       },

       {
            keyword:"DIGITAL_CURRENCY_WEEKLY",
            key:"Weekly Time Series",
            lable:"1 Week",
            value:7,
       },

       {
            keyword:"DIGITAL_CURRENCY_MONTHLY",
            key:"Monthly Time Series",
            lable:"1 Month",
            value:30, 
       }

]

const StockChart = () => {

      const[activeLable,setActiveLable]=useState("1 Day")

      const series = [
            {
                  data: [
                        [
                              1723792066504,
                              58481.39741510699
                        ],
                        [
                              1723796133426,
                              58601.31105458154
                        ],
                        [
                              1723799337472,
                              58349.97587789022
                        ],
                        [
                              1723803524717,
                              58393.5913309717
                        ],
                        [
                              1723807393561,
                              58394.022290466
                        ],
                        [
                              1723810286998,
                              58479.15548513328
                        ],
                        [
                              1723814256433,
                              58107.39617493354
                        ],
                        [
                              1723817012172,
                              58322.02070299983
                        ],
                        [
                              1723821777567,
                              58087.23647718457
                        ],
                        [
                              1723825376763,
                              58366.058274183546
                        ],
                        [
                              1723828798731,
                              58602.833023575986
                        ],
                        [
                              1723832526830,
                              59336.92183147019
                        ],
                        [
                              1723835912382,
                              59546.701473360816
                        ],
                        [
                              1723838679193,
                              59658.822844689734
                        ],
                        [
                              1723842293954,
                              59257.78670650454
                        ],
                        [
                              1723846720385,
                              59079.85864539329
                        ],
                        [
                              1723849229316,
                              59008.35598447069
                        ],
                        [
                              1723853955239,
                              58860.57334808443
                        ],
                        [
                              1723857450178,
                              59217.47771557815
                        ],
                        [
                              1723860536117,
                              59350.615234769495
                        ],
                        [
                              1723863765582,
                              59152.62830970626
                        ],
                        [
                              1723867556498,
                              59169.67657788981
                        ],
                        [
                              1723871918642,
                              59217.21091899589
                        ],
                        [
                              1723875769517,
                              59199.61103327122
                        ],
                        [
                              1723878757430,
                              59102.25419430356
                        ],
                        [
                              1723881804687,
                              59281.64422691288
                        ],
                        [
                              1723885533670,
                              59281.08059915197
                        ],
                        [
                              1723889006713,
                              59339.07971026275
                        ],
                        [
                              1723893210014,
                              59144.78963688992
                        ],
                        [
                              1723896281057,
                              59137.83368710243
                        ]
                  ],
            },
      ];


      const options={
            chart:{
                  id:"area-datetime",
                  type:"area",
                  height:450,
                  zoom:{
                        autoScaleYaxis:true
                  }
            },
            dataLabels:{
                  enables:false
            },
            xaxis:{
                  type:"datetime",
                  tickAmount:6
            },
            colors:["#758AA2"],
            markers:{
                 colors:["#fff"],
                 strokeColor:"#fff",
                 size:0,
                 style:"hollow",
                 strokeWidth:1,
            },

            tooltip:{
                  theme:"dark"
            },
            fill:{
                  type:"gradient",
                  gradient:{
                        shapeIntensity:1,
                        opacityFrom:0.7,
                        opacityTo:0.9,
                        stops:[0,100]
                  }
            },

            grid:{
                  borderColor:"#47535E",
                  strokeDashArray:4,
                  show:true
            }
      };

      const handleActiveLable=(value)=>{
            setActiveLable(value)
      };

      return (
            <div>

                <div className='space-x-3'>
                   {timeSeries.map((item)=>
                     <Button variant={activeLable==item.lable?"":"outline"}
                     onClick={()=>handleActiveLable(item.lable)} key={item.lable}>{item.lable}</Button>
                  )}
                </div>

                  <div id="chart-timelines">
                      <ReactApexChart
                      options={options}
                      series={series}
                      height={450}
                      type="area"
                      />
                  </div>
                  
            </div>
      )
}

export default StockChart