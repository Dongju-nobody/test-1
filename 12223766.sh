#! /bin/bash
echo "---------------------------------------"
echo "User Name: Lee_Dongju"
echo "Student Number: 12223766"
echo "[  MENU  ]"
echo "1. Get the data of the movie identified by a specific 'movie id' from 'u.item'"
echo "2. Get the data of action genre movies from 'u.item'"
echo "3. Get the average 'rating' of the movie identified by specific 'movie id' from 'u.data'"
echo "4. Delte the 'IMDb URL' from 'u.user'"
echo "5. Get the data about users from 'u.user'"
echo "6. Modify the format of 'release date' in 'u.item'"
echo "7. Get the data of movies rated by a specific 'user id' from 'u.data'"
echo "8. Get the average 'rating' of movies rated by users with 'age' between 20 and 29 and 'occupation' as 'programer'"
echo "9. Exit"
echo "---------------------------------------"
choice=0
while [ $choice -ne '9' ]
do 
echo " "
read -p "Enter your choice [ 1-9 ] " choice
echo " "

case $choice in 
        1)
            read -p "Please enter 'movie id' (1~1682):" movie_id 
            awk "NR == $movie_id" u.item
        ;;
        2)
            read -p "Do you want to get the data of 'action' genre movies from 'u.item'? (y/n) :" YorN
            
            case ${YorN} in
                [yY]|'Yes')
                    awk -F'|' '$7 > 0{print $1 " " $2}' u.item |head
                ;;
            esac
        ;;
        3)
            read -p "Please enter 'movie id' (1~1682):" movie_id 
            awk -v id=$movie_id '$2 == id {sum+=$3;num+=1} END {print sum / num}' u.data
        ;;
        4)
            read -p "Do you want to delete the 'IMDB URL' from 'u.item'? (y/n) :" YorN
            case ${YorN} in
                [yY]|'Yes')
                    awk -F"|" '{$5=""; print $0}' u.item|head 
                ;;
            esac
        ;;
        5)
            awk -F'|' '{print "user " $1 " is " $2 " years old " $3 " " $4}' u.user | sed "s/M/male/" |sed "s/F/female/" |head
        ;;
        6)
            while read line
            do
                Date=$(echo $line | cut -d"|" -f3)
                Day=$(echo $Date | cut -d"-" -f1)
                Month=$(echo $Date | cut -d"-" -f2)
                Year=$(echo $Date | cut -d"-" -f3)
                case ${Month} in
                    Jan) Month=01;;
                    Feb) Month=02;;
                    Mar) Month=03;;
                    Apr) Month=04;;
                    May) Month=05;;
                    Jun) Month=06;;
                    Jul) Month=07;;
                    Aug) Month=08;;
                    Sep) Month=09;;
                    Oct) Month=10;;
                    Nov) Month=11;;
                    Dec) Month=12;;
                esac
                echo $line | sed 's/[0-9][0-9]-[A-Z][a-z][a-z]-[0-9][0-9][0-9][0-9]/'$Year$Month$Day'/g'
            done < <(tail -n10 u.item)
        ;;
        7)
            read -p "Please enter the 'user id' (1-943): " user_id
            touch sort1.txt
            awk -v id=$user_id '$1==id{print $2}' u.data|sort -n -t"|" > sort1.txt
            output=$(awk '{print $0"|"}' sort1.txt)
            echo $output
            put=()
            input=$(awk -v arr=$put '{put+=$1;print $1}' sort1.txt |head) 
            for i in $input
            do
                awk -F"|" -v num=$i '$1 == num {print $1"|" $2}' u.item| head
            done
            rm sort1.txt
        ;;
        8)
        count=1
        while [ $count -le 1682 ]
        do
            touch new_ave.txt
            awk -v id=$count '$2==id {print $1 " " $3}' u.data > new_ave.txt
            programmer=$(awk -F"|" '$2 >= 20 && $2 < 30 && $4 == "programmer" {print $1}' u.user)
            sum=0
            num=0
            for i in $programmer 
            do 
                while read line
                do
                    array=(${line})
                    if [ $i == ${array[0]} ];then
                        sum=$(($sum + ${array[1]}))
                        num=$(($num + 1))
                    fi
                done < new_ave.txt
            done
            if [ $num > 0 ];then
                echo $sum  $num | awk -v sum=$sum -v num=$num -v c=$count '{if(num > 0)print c " " sum / num}'
            fi
            rm new_ave.txt
            count=$(($count + 1))
        done
        ;;
        9)
        echo "Bye!"
        exit 0
        ;;
esac
done
