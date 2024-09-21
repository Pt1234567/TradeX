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

const Activity = () => {
  return (
    <div className='p-5 lg:px-20'>

<h1 className='font-bold text-3xl pb-5'>Activity</h1>

<Table className='border'>
      
      <TableHeader>
        <TableRow>
          <TableHead className="py-5">Date & Time</TableHead>
          <TableHead>Trading Coin</TableHead>
          <TableHead>Buy Price</TableHead>
          <TableHead>Sell Price</TableHead>
          <TableHead>Order Type</TableHead>
          <TableHead>Profit/Loss</TableHead>
          <TableHead className='text-right'>Value</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>

       {[1,1,1,1,1,1,1,1,1,1,1,1].map((item,index)=>
           <TableRow key={index}>
           <TableCell>
            <p>2024/05/31</p>
            <p className='text-gray-400'>12:39:32</p>
           </TableCell>
           <TableCell className="font-medium flex items-center gap-2">
             <Avatar className='-z-50 w-10 h-10'>
                   <AvatarImage src='https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400'/>
             </Avatar>
             <span>BitCoin</span>
           </TableCell>
           <TableCell className="text-left">$69249</TableCell>
           <TableCell className="text-left">$69249</TableCell>
           <TableCell>BUY</TableCell>
           <TableCell>-0.221211</TableCell>
           <TableCell className="text-right">$69249</TableCell>
           
        
         </TableRow>
      )}

        

      </TableBody>
    </Table>      
    </div>
  )
}

export default Activity