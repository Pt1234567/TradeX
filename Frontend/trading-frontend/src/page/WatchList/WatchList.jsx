import React from 'react'
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Avatar } from '@radix-ui/react-avatar'
import { AvatarImage } from '@/components/ui/avatar'
import { Button } from '@/components/ui/button'
import { BookmarkFilledIcon } from '@radix-ui/react-icons'

const WatchList = () => {

   const handleRemoveFromWatchList=(value)=>{
    console.log(value)
   }

  return (
    <div className='p-5 lg:px-20'>

<h1 className='font-bold text-3xl pb-5'>Watchlist</h1>

<Table className='border'>
      
      <TableHeader>
        <TableRow>
          <TableHead className="py-5">Coin</TableHead>
          <TableHead>Symbol</TableHead>
          <TableHead>Volume</TableHead>
          <TableHead>Market Cap</TableHead>
          <TableHead>24h</TableHead>
          <TableHead className="text-right">Price</TableHead>
          <TableHead className="text-right text-red-500">Remove</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>

       {[1,1,1,1,1,1,1,1,1,1,1,1].map((item,index)=>
           <TableRow key={index}>

           <TableCell className="font-medium flex items-center gap-2">
             <Avatar className='-z-50 w-10 h-10'>
                   <AvatarImage src='https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400'/>
             </Avatar>
             <span>BitCoin</span>
           </TableCell>
           <TableCell>BTC</TableCell>
           <TableCell>12312313212</TableCell>
           <TableCell>5541232122332</TableCell>
           <TableCell>-0.221211</TableCell>
           <TableCell className="text-right">$69249</TableCell>
           <TableCell className="text-right">
            <Button variant="ghost" onClick={()=>handleRemoveFromWatchList(item.id)} size='icon' className='h-10 w-10'>
              <BookmarkFilledIcon className='w-6 h-6'/>
            </Button>
           </TableCell>
        
         </TableRow>
      )}

        

      </TableBody>
    </Table>      
    </div>
  )
}

export default WatchList