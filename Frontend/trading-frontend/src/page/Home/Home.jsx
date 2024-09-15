import { Button } from '@/components/ui/button'
import React from 'react'
import AssetTable from './AssetTable'
import StockChart from './StockChart'
import { Avatar } from '@radix-ui/react-avatar'
import { AvatarImage } from '@/components/ui/avatar'
import { DotIcon } from 'lucide-react'

const Home = () => {

      const [category,setCategory]=React.useState("all")
      const handleCategory=(value)=>{
            setCategory(value)
      }

  return (
    <div className='relative'>
          <div className='lg:flex'>
                  <div className='lg:w-[50%] lg:border-r'>
                        <div className='p-3 flex items-center gap-4'>

                           <Button onClick={()=>handleCategory("all")} variant={category=="all"?"default":"outline"} className='rounded-full'>
                              All
                           </Button>

                           <Button onClick={()=>handleCategory("top50")} variant={category=="top50"?"default":"outline"} className='rounded-full'>
                              Top 50
                           </Button>

                           <Button onClick={()=>handleCategory("topGainers")} variant={category=="topGainers"?"default":"outline"} className='rounded-full'>
                              Top Gainers
                           </Button>

                           <Button onClick={()=>handleCategory("topLosers")} variant={category=="topLosers"?"default":"outline"} className='rounded-full'>
                              Top Losers
                           </Button>

                        </div>
                        <AssetTable/>

                  </div>
                  <div className='hidden lg:block lg:w-[50%] p-5'>
                      <StockChart/>

                      <div className='flex items-center gap-2'>
                        <div className='font-medium flex items-center'>
                              <Avatar className='w-10 h-10'>
                                    <AvatarImage src='https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400'/>
                              </Avatar>
                        </div>
                        <div>
                        <div className='flex items-center gap-2'>
         
         <p>BTC</p>
         <DotIcon className='text-gray-400'/>
         <p className='text-gray-400'>BitCoin</p>
 </div>

 <div className='flex items-end gap-2'>

   <p className='text-l font-medium'>5464</p>
   <p className='text-red-600'>
         <span>-1319049822.5788</span>
         <span>(-0.29803%)</span>
   </p>

 </div>
                        </div>

                    

                      </div>

                  </div>
          </div>
    </div>
  )
}

export default Home