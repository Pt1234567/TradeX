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
    

const AssetTable = () => {
  return (
      <Table>
      
      <TableHeader>
        <TableRow>
          <TableHead className="w-[100px]">Coin</TableHead>
          <TableHead>Symbol</TableHead>
          <TableHead>Volume</TableHead>
          <TableHead>Market Cap</TableHead>
          <TableHead>24h</TableHead>
          <TableHead className="text-right">Price</TableHead>
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
 
         </TableRow>
      )}

        

      </TableBody>
    </Table>
    
  )
}

export default AssetTable