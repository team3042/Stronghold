package org.usfirst.frc.team3042.robot.commands;

public class AutoTrajectory_LowBarSideGoal {
	public static double[][] getLeftTrajectory() {
		return LeftPoints;
	}
	
	public static double[][] getRightTrajectory() {
		return RightPoints;
	}
	
	// Position (rotations)	Velocity (RPM)	Duration (ms)
	public static double [][]LeftPoints = new double[][]{		
	{0,	0,	20},
	{0.000183673698515115,	0.551021095545347,	20},
	{0.00082653164331802,	1.92857383440871,	20},
	{0.00211224753292383,	3.85714766881742,	20},
	{0.00422449506584766,	6.33674259877146,	20},
	{0.00734694794060462,	9.36735862427088,	20},
	{0.0116632798557098,	12.9489957453157,	20},
	{0.0173571645096784,	17.0816539619059,	20},
	{0.0246122756010255,	21.7653332740413,	20},
	{0.0336122868282663,	27.0000336817222,	20},
	{0.0445408718899155,	32.7857551849478,	20},
	{0.0574898676352308,	38.8469872359458,	20},
	{0.0724592740642123,	44.9082192869445,	20},
	{0.0894490911768601,	50.9694513379432,	20},
	{0.108459318973174,	57.0306833889418,	20},
	{0.129489957453154,	63.09191543994,	20},
	{0.1525410066168,	69.1531474909386,	20},
	{0.177612466464115,	75.214379541945,	20},
	{0.204704336995098,	81.2756115929472,	20},
	{0.233816618209747,	87.336843643947,	20},
	{0.264949310108062,	93.3980756949462,	20},
	{0.297918738991529,	98.9082866504004,	20},
	{0.332449394312375,	103.591965962536,	20},
	{0.368357602372084,	107.724624179127,	20},
	{0.405459689472141,	111.306261300172,	20},
	{0.443571981914032,	114.336877325672,	20},
	{0.482510805999241,	116.816472255626,	20},
	{0.522092488029253,	118.745046090035,	20},
	{0.562133354305552,	120.122598828899,	20},
	{0.602449731129625,	120.949130472217,	20},
	{0.642857944802954,	121.224641019989,	20},
	{0.683266158476284,	121.224641019989,	20},
	{0.723674372149614,	121.224641019989,	20},
	{0.764082585822944,	121.224641019989,	20},
	{0.804490799496274,	121.22464101999,	20},
	{0.844899013169604,	121.224641019989,	20},
	{0.885307226842934,	121.224641019989,	20},
	{0.925715440516263,	121.224641019988,	20},
	{0.966123654189593,	121.22464101999,	20},
	{1.00653186786292,	121.224641019989,	20},
	{1.04694008153625,	121.224641019989,	20},
	{1.08734829520958,	121.22464101999,	20},
	{1.12775650888291,	121.224641019989,	20},
	{1.16816472255624,	121.224641019988,	20},
	{1.20857293622957,	121.22464101999,	20},
	{1.2489811499029,	121.224641019989,	20},
	{1.28938936357623,	121.224641019989,	20},
	{1.32979757724956,	121.224641019989,	20},
	{1.37020579092289,	121.22464101999,	20},
	{1.41061400459622,	121.224641019989,	20},
	{1.45102221826955,	121.22464101999,	20},
	{1.49143043194288,	121.224641019989,	20},
	{1.53183864561621,	121.224641019989,	20},
	{1.57224685928954,	121.22464101999,	20},
	{1.61265507296287,	121.224641019988,	20},
	{1.6530632866362,	121.22464101999,	20},
	{1.69347150030953,	121.22464101999,	20},
	{1.73387971398286,	121.224641019988,	20},
	{1.77428792765619,	121.22464101999,	20},
	{1.81469614132952,	121.22464101999,	20},
	{1.85510435500285,	121.224641019989,	20},
	{1.89551256867618,	121.22464101999,	20},
	{1.93592078234951,	121.224641019989,	20},
	{1.97632899602284,	121.22464101999,	20},
	{2.01673720969617,	121.224641019989,	20},
	{2.0571454233695,	121.224641019989,	20},
	{2.09755363704283,	121.22464101999,	20},
	{2.13796185071616,	121.22464101999,	20},
	{2.17837006438949,	121.224641019988,	20},
	{2.21877827806282,	121.224641019989,	20},
	{2.25918649173615,	121.22464101999,	20},
	{2.29959470540948,	121.224641019989,	20},
	{2.34000291908281,	121.22464101999,	20},
	{2.38041113275614,	121.22464101999,	20},
	{2.42081934642947,	121.224641019989,	20},
	{2.4612275601028,	121.224641019989,	20},
	{2.50163577377613,	121.22464101999,	20},
	{2.54204398744946,	121.224641019989,	20},
	{2.58245220112271,	121.224641019749,	20},
	{2.62286041479595,	121.224641019719,	20},
	{2.66326862846919,	121.224641019721,	20},
	{2.70367684214243,	121.224641019719,	20},
	{2.74408505581567,	121.224641019719,	20},
	{2.78449326948891,	121.224641019722,	20},
	{2.82490148316215,	121.224641019719,	20},
	{2.86530969683539,	121.224641019719,	20},
	{2.90571791050863,	121.224641019721,	20},
	{2.94612612418187,	121.224641019721,	20},
	{2.98653433785511,	121.224641019719,	20},
	{3.02694255152835,	121.224641019721,	20},
	{3.06735076520159,	121.224641019719,	20},
	{3.10775897887483,	121.224641019719,	20},
	{3.14816719254807,	121.224641019721,	20},
	{3.18857540622131,	121.224641019721,	20},
	{3.22898361989455,	121.224641019719,	20},
	{3.26939183356779,	121.224641019721,	20},
	{3.30980004724103,	121.224641019719,	20},
	{3.35020826091427,	121.224641019719,	20},
	{3.39061647458751,	121.224641019721,	20},
	{3.43102468826075,	121.224641019721,	20},
	{3.47143290193399,	121.224641019717,	20},
	{3.51184111560723,	121.224641019724,	20},
	{3.55224932928047,	121.224641019719,	20},
	{3.59265754295371,	121.224641019717,	20},
	{3.63306575662695,	121.224641019721,	20},
	{3.67347397030019,	121.224641019721,	20},
	{3.71388218397343,	121.224641019719,	20},
	{3.75429039764667,	121.224641019721,	20},
	{3.79469861131991,	121.224641019719,	20},
	{3.83510682499315,	121.224641019719,	20},
	{3.87551503866639,	121.224641019722,	20},
	{3.91592325233963,	121.224641019719,	20},
	{3.95633146601287,	121.224641019721,	20},
	{3.99673967968611,	121.224641019721,	20},
	{4.03714789335935,	121.224641019717,	20},
	{4.07755610703259,	121.224641019719,	20},
	{4.11796432070583,	121.224641019721,	20},
	{4.15837253437907,	121.224641019721,	20},
	{4.19878074805231,	121.224641019719,	20},
	{4.23918896172555,	121.224641019719,	20},
	{4.27959717539879,	121.224641019722,	20},
	{4.32000538907203,	121.224641019719,	20},
	{4.36041360274527,	121.224641019719,	20},
	{4.40082181641851,	121.224641019721,	20},
	{4.44123003009175,	121.224641019721,	20},
	{4.48163824376499,	121.224641019719,	20},
	{4.52204645743823,	121.224641019719,	20},
	{4.56245467111147,	121.224641019719,	20},
	{4.60286288478471,	121.224641019721,	20},
	{4.64327109845795,	121.224641019721,	20},
	{4.68367931213119,	121.224641019721,	20},
	{4.72408752580443,	121.224641019721,	20},
	{4.76449573947767,	121.224641019719,	20},
	{4.80490395315091,	121.224641019719,	20},
	{4.84531216682415,	121.224641019719,	20},
	{4.88572038049739,	121.224641019721,	20},
	{4.92612859417063,	121.224641019721,	20},
	{4.96653680784387,	121.224641019717,	20},
	{5.00694502151711,	121.224641019722,	20},
	{5.04735323519035,	121.224641019721,	20},
	{5.08776144886359,	121.224641019717,	20},
	{5.12816966253698,	121.224641020194,	20},
	{5.1685778762104,	121.224641020258,	20},
	{5.20898608988382,	121.224641020262,	20},
	{5.24939430355724,	121.224641020258,	20},
	{5.28980251723066,	121.224641020262,	20},
	{5.33021073090408,	121.224641020258,	20},
	{5.3706189445775,	121.224641020258,	20},
	{5.41102715825092,	121.224641020262,	20},
	{5.45143537192434,	121.224641020258,	20},
	{5.49184358559776,	121.224641020262,	20},
	{5.53225179927118,	121.224641020258,	20},
	{5.5726600129446,	121.224641020262,	20},
	{5.61306822661802,	121.224641020258,	20},
	{5.65347644029144,	121.224641020258,	20},
	{5.69388465396486,	121.224641020262,	20},
	{5.73429286763828,	121.224641020262,	20},
	{5.7747010813117,	121.224641020258,	20},
	{5.81510929498512,	121.224641020258,	20},
	{5.85551750865854,	121.224641020262,	20},
	{5.89592572233196,	121.224641020258,	20},
	{5.93633393600538,	121.224641020262,	20},
	{5.9767421496788,	121.224641020258,	20},
	{6.01715036335222,	121.224641020262,	20},
	{6.05755857702564,	121.224641020258,	20},
	{6.09796679069906,	121.224641020258,	20},
	{6.13837500437248,	121.224641020258,	20},
	{6.1787832180459,	121.224641020262,	20},
	{6.21919143171932,	121.224641020262,	20},
	{6.25959964539274,	121.224641020258,	20},
	{6.30000785906616,	121.224641020262,	20},
	{6.34041607273958,	121.224641020258,	20},
	{6.38081853962704,	121.207400662384,	20},
	{6.42115298138551,	121.003325275389,	20},
	{6.46140707416636,	120.762278342564,	20},
	{6.50158670136991,	120.538881610631,	20},
	{6.54169768474561,	120.332950127098,	20},
	{6.58174577703738,	120.144276875316,	20},
	{6.62173665544108,	119.972635211118,	20},
	{6.66167591570679,	119.81778079713,	20},
	{6.70156906710187,	119.679454185222,	20},
	{6.74142152784569,	119.557382231477,	20},
	{6.78123862131877,	119.451280419215,	20},
	{6.82102557284847,	119.360854589125,	20},
	{6.8607875070811,	119.285802697878,	20},
	{6.90052944589887,	119.225816453306,	20},
	{6.94025630687539,	119.180582929567,	20},
	{6.97997290222072,	119.149786035996,	20},
	{7.01968393813127,	119.133107731633,	20},
	{7.05939401482105,	119.130230069347,	20},
	{7.09910762659011,	119.140835307163,	20},
	{7.13882916273074,	119.164608421905,	20},
	{7.17856290839969,	119.201237006856,	20},
	{7.21831304608058,	119.250413042665,	20},
	{7.2580836572942,	119.31183364085,	20},
	{7.29787872460323,	119.385201927082,	20},
	{7.33770213388798,	119.470227854273,	20},
	{7.37755767685937,	119.566628914172,	20},
	{7.41744905379133,	119.674130795879,	20},
	{7.45737987659391,	119.792468407747,	20},
	{7.4973536717473,	119.921385460153,	20},
	{7.53737388383967,	120.060636277113,	20},
	{7.57744387881494,	120.209984925822,	20},
	{7.61756694764893,	120.369206501974,	20},
	{7.65774631000745,	120.53808707554,	20},
	{7.69798511797711,	120.716423908975,	20},
	{7.73828645991092,	120.904025801432,	20},
	{7.77865336431197,	121.100713203171,	20},
	{7.81908880374918,	121.306318311608,	20},
	{7.85959569878322,	121.520685102116,	20},
	{7.90017692191178,	121.743669385687,	20},
	{7.94083530149758,	121.975138757411,	20},
	{7.98157362570171,	122.214972612381,	20},
	{8.02239464627257,	122.463061712593,	20},
	{8.06330108240962,	122.719308411148,	20},
	{8.10429562457167,	122.983626486136,	20},
	{8.14538093791764,	123.255940037911,	20},
	{8.18655966616136,	123.536184731152,	20},
	{8.22783443475279,	123.824305774295,	20},
	{8.2692078543474,	124.12025878383,	20},
	{8.31068252395446,	124.424008821177,	20},
	{8.35226103392607,	124.735529914844,	20},
	{8.3939459689002,	125.054804922383,	20},
	{8.43573991023041,	125.381823990636,	20},
	{8.47764543875568,	125.716585575817,	20},
	{8.51966513662506,	126.059093608147,	20},
	{8.56180158957973,	126.409358864,	20},
	{8.6040573884491,	126.767396608094,	20},
	{8.64643513054928,	127.133226300546,	20},
	{8.68893742075023,	127.506870602856,	20},
	{8.73156687220555,	127.888354365964,	20},
	{8.7743261066712,	128.277703396952,	20},
	{8.81721775428884,	128.674942852907,	20},
	{8.86024445329125,	129.080097007224,	20},
	{8.90340884843677,	129.493185436584,	20},
	{8.94671359018541,	129.914225245907,	20},
	{8.99016133185673,	130.343225013949,	20},
	{9.03375472763895,	130.780187346664,	20},
	{9.07749642859969,	131.225102882237,	20},
	{9.12138907857173,	131.677949916104,	20},
	{9.16543530995901,	132.138694161847,	20},
	{9.20963773753505,	132.60728272813,	20},
	{9.25399895222163,	133.083644059718,	20},
	{9.29852151370708,	133.567684456345,	20},
	{9.34320794203545,	134.059284985115,	20},
	{9.38806070848187,	134.558299339272,	20},
	{9.43308222494842,	135.064549399642,	20},
	{9.47827483289222,	135.577823831394,	20},
	{9.52364079018671,	136.097871883495,	20},
	{9.56918225829447,	136.624404323253,	20},
	{9.61490128638842,	137.157084281859,	20},
	{9.66079979560613,	137.695527653131,	20},
	{9.70687956187962,	138.239298820483,	20},
	{9.75314219759745,	138.787907153493,	20},
	{9.7995891309404,	139.34080002884,	20},
	{9.84622158640046,	139.897366380172,	20},
	{9.8930405614026,	140.456925006412,	20},
	{9.94004680472829,	141.018729977066,	20},
	{9.98724079182653,	141.58196129475,	20},
	{10.0346227008004,	142.14572692166,	20},
	{10.0821923873625,	142.7090596863,	20},
	{10.1299493590963,	143.270915201577,	20},
	{10.1778927505127,	143.830174248988,	20},
	{10.2260212974899,	144.38564093162,	20},
	{10.274333312171,	144.9360440435,	20},
	{10.3228266597445,	145.480042720246,	20},
	{10.3714987350553,	146.016225932395,	20},
	{10.4203464418002,	146.543120234764,	20},
	{10.4693661734734,	147.059195019668,	20},
	{10.5185537964749,	147.562869004649,	20},
	{10.567904636853,	148.052521134299,	20},
	{10.6174134696547,	148.526498404957,	20},
	{10.6670745128113,	148.983129469644,	20},
	{10.7168814246062,	149.42073538484,	20},
	{10.7668273061444,	149.837644614678,	20},
	{10.8169047087596,	150.232207845568,	20},
	{10.8671056463132,	150.602812660853,	20},
	{10.9174216131547,	150.947900524354,	20},
	{10.9678436073298,	151.265982525394,	20},
	{11.0183621594021,	151.555656216929,	20},
	{11.0689673664931,	151.815621273034,	20},
	{11.1196489316093,	152.044695348363,	20},
	{11.1703962076174,	152.241828024342,	20},
	{11.2211982456525,	152.406114105283,	20},
	{11.2720438474839,	152.536805494189,	20},
	{11.3229216206909,	152.633319621247,	20},
	{11.3738200367829,	152.69524827585,	20},
	{11.4247274911115,	152.722362985934,	20},
	{11.475632362901,	152.714615368367,	20},
	{11.5265230760294,	152.672139385093,	20},
	{11.5773881588266,	152.595248391784,	20},
	{11.6282163028152,	152.484431965803,	20},
	{11.6789964174461,	152.340343892627,	20},
	{11.7297176839272,	152.163799443277,	20},
	{11.7803696033968,	151.955758408897,	20},
	{11.8309420419503,	151.717315660326,	20},
	{11.8814252704588,	151.449685525561,	20},
	{11.9318099991531,	151.154186082869,	20},
	{11.9820874072961,	150.832224429222,	20},
	{12.0322491675258,	150.485280688874,	20},
	{12.0822874637333,	150.114888622613,	20},
	{12.132195004521,	149.722622363206,	20},
	{12.1819650306609,	149.310078419434,	20},
	{12.231591318809,	148.87886444446,	20},
	{12.2810681791233,	148.430580942973,	20},
	{12.3303904492511,	147.966810383258,	20},
	{12.3795534841502,	147.489104697211,	20},
	{12.4285531432507,	146.998977301675,	20},
	{12.4773857720744,	146.497886471152,	20},
	{12.5260481859502,	145.987241627339,	20},
	{12.5745376460804,	145.468380390432,	20},
	{12.6228518369431,	144.942572588327,	20},
	{12.6709888430776,	144.411018403405,	20},
	{12.7189471214937,	143.87483524833,	20},
	{12.7667254773799,	143.335067658679,	20},
	{12.8143230371155,	142.792679206573,	20},
	{12.8617392206232,	142.248550523308,	20},
	{12.9089737158608,	141.703485712678,	20},
	{12.9560264533698,	141.158212526987,	20},
	{13.0028975805947,	140.613381674741,	20},
	{13.0495874348772,	140.069562847537,	20},
	{13.0960965232121,	139.527265004585,	20},
	{13.142425496543,	138.986919992797,	20},
	{13.1885751279258,	138.448894148482,	20},
	{13.2345462923077,	137.913493145509,	20},
	{13.2803399461742,	137.380961599649,	20},
	{13.325957106634,	136.851481379285,	20},
	{13.3713988361576,	136.325188570973,	20},
	{13.4166662232424,	135.802161254308,	20},
	{13.4617603656019,	135.282427078363,	20},
	{13.5066823574943,	134.765975677231,	20},
	{13.5514332726154,	134.252745363377,	20},
	{13.5960141497843,	133.742631506608,	20},
	{13.6404259832076,	133.235500269876,	20},
	{13.6846697051725,	132.731165894799,	20},
	{13.7287461781816,	132.229419027307,	20},
	{13.7726561809383,	131.730008270098,	20},
	{13.8164003980365,	131.232651294578,	20},
	{13.8599794081499,	130.73703034036,	20},
	{13.9033936756604,	130.242802531487,	20},
	{13.9466435377758,	129.749586346207,	20},
	{13.9897291952952,	129.256972558084,	20},
	{14.0326507020522,	128.764520270893,	20},
	{14.075213975316,	127.689819791531,	20},
	{14.1171311172251,	125.75142572723,	20},
	{14.1582147127591,	123.250786602052,	20},
	{14.1982800241765,	120.1959342523,	20},
	{14.2371449001288,	116.594627856895,	20},
	{14.2746296612054,	112.454283229715,	20},
	{14.3105569610815,	107.781899628308,	20},
	{14.3447516141653,	102.583959251513,	20},
	{14.3770403887754,	96.8663238302994,	20},
	{14.407251757681,	90.6341067166104,	20},
	{14.4353093052601,	84.1726427372472,	20},
	{14.4612301249521,	77.7624590761356,	20},
	{14.4850302423698,	71.4003522529512,	20},
	{14.5067245934872,	65.0830533524226,	20},
	{14.5263269966901,	58.8072096086971,	20},
	{14.5438501206816,	52.5693719744149,	20},
	{14.5593054487948,	46.3659843397379,	20},
	{14.5727032421694,	40.1933801236238,	20},
	{14.5840525025689,	34.0477811984837,	20},
	{14.5933609368876,	27.925302956058,	20},
	{14.6008189973819,	22.3741814829091,	20},
	{14.6067072877043,	17.6648709672832,	20},
	{14.6112128181517,	13.5165913422027,	20},
	{14.6145215127463,	9.92608378386594,	20},
	{14.616818472975,	6.89088068617032,	20},
	{14.6182882069452,	4.40920191040724,	20},
	{14.6191148222637,	2.47984595540999,	20},
	{14.6194821814066,	1.10207742886978,	20},
	{14.6195740187453,	0.275512016161944,	20},
	{14.6195740187453,	0,	20}};
	
	// Position (rotations)	Velocity (RPM)	Duration (ms)
	public static double [][]RightPoints = new double[][]{		
		{0,	0,	20},
		{0.000183673698515115,	0.551021095545347,	20},
		{0.00082653164331802,	1.92857383440871,	20},
		{0.00211224753292383,	3.85714766881742,	20},
		{0.00422449506584766,	6.33674259877146,	20},
		{0.00734694794060462,	9.36735862427088,	20},
		{0.0116632798557098,	12.9489957453157,	20},
		{0.0173571645096784,	17.0816539619059,	20},
		{0.0246122756010255,	21.7653332740413,	20},
		{0.0336122868282663,	27.0000336817222,	20},
		{0.0445408718899155,	32.7857551849478,	20},
		{0.0574898676352308,	38.8469872359458,	20},
		{0.0724592740642123,	44.9082192869445,	20},
		{0.0894490911768601,	50.9694513379432,	20},
		{0.108459318973174,	57.0306833889418,	20},
		{0.129489957453154,	63.09191543994,	20},
		{0.1525410066168,	69.1531474909386,	20},
		{0.177612466464115,	75.214379541945,	20},
		{0.204704336995098,	81.2756115929472,	20},
		{0.233816618209747,	87.336843643947,	20},
		{0.264949310108062,	93.3980756949462,	20},
		{0.297918738991529,	98.9082866504004,	20},
		{0.332449394312375,	103.591965962536,	20},
		{0.368357602372084,	107.724624179127,	20},
		{0.405459689472141,	111.306261300172,	20},
		{0.443571981914032,	114.336877325672,	20},
		{0.482510805999241,	116.816472255626,	20},
		{0.522092488029253,	118.745046090035,	20},
		{0.562133354305552,	120.122598828899,	20},
		{0.602449731129625,	120.949130472217,	20},
		{0.642857944802954,	121.224641019989,	20},
		{0.683266158476284,	121.224641019989,	20},
		{0.723674372149614,	121.224641019989,	20},
		{0.764082585822944,	121.224641019989,	20},
		{0.804490799496274,	121.22464101999,	20},
		{0.844899013169604,	121.224641019989,	20},
		{0.885307226842934,	121.224641019989,	20},
		{0.925715440516263,	121.224641019988,	20},
		{0.966123654189593,	121.22464101999,	20},
		{1.00653186786292,	121.224641019989,	20},
		{1.04694008153625,	121.224641019989,	20},
		{1.08734829520958,	121.22464101999,	20},
		{1.12775650888291,	121.224641019989,	20},
		{1.16816472255624,	121.224641019988,	20},
		{1.20857293622957,	121.22464101999,	20},
		{1.2489811499029,	121.224641019989,	20},
		{1.28938936357623,	121.224641019989,	20},
		{1.32979757724956,	121.224641019989,	20},
		{1.37020579092289,	121.22464101999,	20},
		{1.41061400459622,	121.224641019989,	20},
		{1.45102221826955,	121.22464101999,	20},
		{1.49143043194288,	121.224641019989,	20},
		{1.53183864561621,	121.224641019989,	20},
		{1.57224685928954,	121.22464101999,	20},
		{1.61265507296287,	121.224641019988,	20},
		{1.6530632866362,	121.22464101999,	20},
		{1.69347150030953,	121.22464101999,	20},
		{1.73387971398286,	121.224641019988,	20},
		{1.77428792765619,	121.22464101999,	20},
		{1.81469614132952,	121.22464101999,	20},
		{1.85510435500285,	121.224641019989,	20},
		{1.89551256867618,	121.22464101999,	20},
		{1.93592078234951,	121.224641019989,	20},
		{1.97632899602284,	121.22464101999,	20},
		{2.01673720969617,	121.224641019989,	20},
		{2.0571454233695,	121.224641019989,	20},
		{2.09755363704283,	121.22464101999,	20},
		{2.13796185071616,	121.22464101999,	20},
		{2.17837006438949,	121.224641019988,	20},
		{2.21877827806282,	121.224641019989,	20},
		{2.25918649173615,	121.22464101999,	20},
		{2.29959470540948,	121.224641019989,	20},
		{2.34000291908281,	121.22464101999,	20},
		{2.38041113275614,	121.22464101999,	20},
		{2.42081934642947,	121.224641019989,	20},
		{2.4612275601028,	121.224641019989,	20},
		{2.50163577377613,	121.22464101999,	20},
		{2.54204398744946,	121.224641019989,	20},
		{2.58245220112271,	121.224641019749,	20},
		{2.62286041479595,	121.224641019719,	20},
		{2.66326862846919,	121.224641019721,	20},
		{2.70367684214243,	121.224641019719,	20},
		{2.74408505581567,	121.224641019719,	20},
		{2.78449326948891,	121.224641019722,	20},
		{2.82490148316215,	121.224641019719,	20},
		{2.86530969683539,	121.224641019719,	20},
		{2.90571791050863,	121.224641019721,	20},
		{2.94612612418187,	121.224641019721,	20},
		{2.98653433785511,	121.224641019719,	20},
		{3.02694255152835,	121.224641019721,	20},
		{3.06735076520159,	121.224641019719,	20},
		{3.10775897887483,	121.224641019719,	20},
		{3.14816719254807,	121.224641019721,	20},
		{3.18857540622131,	121.224641019721,	20},
		{3.22898361989455,	121.224641019719,	20},
		{3.26939183356779,	121.224641019721,	20},
		{3.30980004724103,	121.224641019719,	20},
		{3.35020826091427,	121.224641019719,	20},
		{3.39061647458751,	121.224641019721,	20},
		{3.43102468826075,	121.224641019721,	20},
		{3.47143290193399,	121.224641019717,	20},
		{3.51184111560723,	121.224641019724,	20},
		{3.55224932928047,	121.224641019719,	20},
		{3.59265754295371,	121.224641019717,	20},
		{3.63306575662695,	121.224641019721,	20},
		{3.67347397030019,	121.224641019721,	20},
		{3.71388218397343,	121.224641019719,	20},
		{3.75429039764667,	121.224641019721,	20},
		{3.79469861131991,	121.224641019719,	20},
		{3.83510682499315,	121.224641019719,	20},
		{3.87551503866639,	121.224641019722,	20},
		{3.91592325233963,	121.224641019719,	20},
		{3.95633146601287,	121.224641019721,	20},
		{3.99673967968611,	121.224641019721,	20},
		{4.03714789335935,	121.224641019717,	20},
		{4.07755610703259,	121.224641019719,	20},
		{4.11796432070583,	121.224641019721,	20},
		{4.15837253437907,	121.224641019721,	20},
		{4.19878074805231,	121.224641019719,	20},
		{4.23918896172555,	121.224641019719,	20},
		{4.27959717539879,	121.224641019722,	20},
		{4.32000538907203,	121.224641019719,	20},
		{4.36041360274527,	121.224641019719,	20},
		{4.40082181641851,	121.224641019721,	20},
		{4.44123003009175,	121.224641019721,	20},
		{4.48163824376499,	121.224641019719,	20},
		{4.52204645743823,	121.224641019719,	20},
		{4.56245467111147,	121.224641019719,	20},
		{4.60286288478471,	121.224641019721,	20},
		{4.64327109845795,	121.224641019721,	20},
		{4.68367931213119,	121.224641019721,	20},
		{4.72408752580443,	121.224641019721,	20},
		{4.76449573947767,	121.224641019719,	20},
		{4.80490395315091,	121.224641019719,	20},
		{4.84531216682415,	121.224641019719,	20},
		{4.88572038049739,	121.224641019721,	20},
		{4.92612859417063,	121.224641019721,	20},
		{4.96653680784387,	121.224641019717,	20},
		{5.00694502151711,	121.224641019722,	20},
		{5.04735323519035,	121.224641019721,	20},
		{5.08776144886359,	121.224641019717,	20},
		{5.12816966253698,	121.224641020194,	20},
		{5.1685778762104,	121.224641020258,	20},
		{5.20898608988382,	121.224641020262,	20},
		{5.24939430355724,	121.224641020258,	20},
		{5.28980251723066,	121.224641020262,	20},
		{5.33021073090408,	121.224641020258,	20},
		{5.3706189445775,	121.224641020258,	20},
		{5.41102715825092,	121.224641020262,	20},
		{5.45143537192434,	121.224641020258,	20},
		{5.49184358559776,	121.224641020262,	20},
		{5.53225179927118,	121.224641020258,	20},
		{5.5726600129446,	121.224641020262,	20},
		{5.61306822661802,	121.224641020258,	20},
		{5.65347644029144,	121.224641020258,	20},
		{5.69388465396486,	121.224641020262,	20},
		{5.73429286763828,	121.224641020262,	20},
		{5.7747010813117,	121.224641020258,	20},
		{5.81510929498512,	121.224641020258,	20},
		{5.85551750865854,	121.224641020262,	20},
		{5.89592572233196,	121.224641020258,	20},
		{5.93633393600538,	121.224641020262,	20},
		{5.9767421496788,	121.224641020258,	20},
		{6.01715036335222,	121.224641020262,	20},
		{6.05755857702564,	121.224641020258,	20},
		{6.09796679069906,	121.224641020258,	20},
		{6.13837500437248,	121.224641020258,	20},
		{6.1787832180459,	121.224641020262,	20},
		{6.21919143171932,	121.224641020262,	20},
		{6.25959964539274,	121.224641020258,	20},
		{6.30000785906616,	121.224641020262,	20},
		{6.34041607273958,	121.224641020258,	20},
		{6.38083003275259,	121.241880039014,	20},
		{6.42131201259143,	121.445939516533,	20},
		{6.46187433508892,	121.686967492477,	20},
		{6.50251111721667,	121.910346383239,	20},
		{6.54321653762657,	122.1162612297,	20},
		{6.58398484396115,	122.30491900373,	20},
		{6.62481035945122,	122.476546470203,	20},
		{6.66568748869384,	122.631387727883,	20},
		{6.70661072289341,	122.769702598713,	20},
		{6.7475746442293,	122.891764007664,	20},
		{6.78857392971953,	122.997856470671,	20},
		{6.82960335443756,	123.088274154104,	20},
		{6.87065779414998,	123.163319137244,	20},
		{6.91173222738976,	123.223299719354,	20},
		{6.95282173701489,	123.268528875397,	20},
		{6.99392151125742,	123.29932272758,	20},
		{7.03502684423011,	123.315998918079,	20},
		{7.07613313623109,	123.318876002938,	20},
		{7.1172358932328,	123.308271005139,	20},
		{7.15833072643667,	123.28449961161,	20},
		{7.19941335104245,	123.247873817331,	20},
		{7.24047958492938,	123.198701660777,	20},
		{7.28152534694445,	123.137286045232,	20},
		{7.3225466548949,	123.06392385133,	20},
		{7.36353962326839,	122.978905120487,	20},
		{7.40450046069296,	122.882512273709,	20},
		{7.44542546716385,	122.77501941267,	20},
		{7.48631103120562,	122.656692125294,	20},
		{7.52715362652059,	122.527785944933,	20},
		{7.56794980892689,	122.388547218883,	20},
		{7.60869621271085,	122.239211351886,	20},
		{7.64938954716836,	122.080003372517,	20},
		{7.69002659291601,	121.911137242948,	20},
		{7.73060419810225,	121.732815558746,	20},
		{7.77111927459609,	121.545229481521,	20},
		{7.81156879410951,	121.34855854024,	20},
		{7.85194978428062,	121.142970513345,	20},
		{7.89225932472777,	120.928621341429,	20},
		{7.93249454311424,	120.705655159414,	20},
		{7.9726526112175,	120.474204309779,	20},
		{8.01273074105425,	120.234389510248,	20},
		{8.05272618094289,	119.986319665916,	20},
		{8.09263621174794,	119.730092415159,	20},
		{8.1324581432048,	119.465794370576,	20},
		{8.17218931005111,	119.193500538926,	20},
		{8.21182706875253,	118.913276104272,	20},
		{8.25136879379618,	118.625175130952,	20},
		{8.29081187451345,	118.329242151791,	20},
		{8.33015371187262,	118.025512077525,	20},
		{8.36939171543075,	117.714010674386,	20},
		{8.40852330058339,	117.394755457924,	20},
		{8.44754588570699,	117.067755370801,	20},
		{8.48645689003144,	116.733012973342,	20},
		{8.52525373107787,	116.390523139291,	20},
		{8.56393382301217,	116.040275802894,	20},
		{8.60249457479976,	115.682255362772,	20},
		{8.64093338884935,	115.316442148777,	20},
		{8.67924765997716,	114.942813383413,	20},
		{8.71743477473895,	114.561344285394,	20},
		{8.75549211111985,	114.172009142702,	20},
		{8.79341703853298,	113.774782239362,	20},
		{8.83120691859604,	113.369640189187,	20},
		{8.86885910570058,	112.956561313613,	20},
		{8.90637094921554,	112.535530544897,	20},
		{8.94373979498145,	112.106537297718,	20},
		{8.98096298867745,	111.669581088002,	20},
		{9.01803787867425,	111.224669990395,	20},
		{9.05496182008749,	110.771824239736,	20},
		{9.09173218013267,	110.311080135553,	20},
		{9.12834634325088,	109.84248935462,	20},
		{9.16480171777927,	109.366123585174,	20},
		{9.2010957432947,	108.882076546288,	20},
		{9.23722589883934,	108.390466633915,	20},
		{9.27318971237301,	107.891440601006,	20},
		{9.30898477091765,	107.385175633922,	20},
		{9.34460873228767,	106.871884110061,	20},
		{9.38005933720333,	106.351814746969,	20},
		{9.41533442372624,	105.825259568726,	20},
		{9.45043194142244,	105.292553088606,	20},
		{9.48534996778412,	104.75407908505,	20},
		{9.52008672568619,	104.210273706199,	20},
		{9.55464060206396,	103.661629133306,	20},
		{9.58901016691432,	103.108694551078,	20},
		{9.62319419516176,	102.552084742322,	20},
		{9.65719168749413,	101.992476997109,	20},
		{9.69100189423225,	101.430620214376,	20},
		{9.7246243384186,	100.867332559034,	20},
		{9.75805884064754,	100.303506686842,	20},
		{9.79130554408671,	99.7401103174992,	20},
		{9.8243649395932,	99.1781865194532,	20},
		{9.85723789154925,	98.6188558681602,	20},
		{9.88992566295864,	98.0633142281652,	20},
		{9.92242994003751,	97.5128312366022,	20},
		{9.95475285667331,	96.9687499074174,	20},
		{9.9868970167339,	96.432480181767,	20},
		{10.0188655154124,	95.9054960355708,	20},
		{10.0506619583291,	95.389328750253,	20},
		{10.0822904779417,	94.8855588377136,	20},
		{10.1137557474752,	94.3958086005816,	20},
		{10.1450629909707,	93.9217304863848,	20},
		{10.1762179899767,	93.4649970181032,	20},
		{10.2072270856035,	93.0272868803208,	20},
		{10.238097176231,	92.6102718825462,	20},
		{10.2688357102996,	92.2156022056626,	20},
		{10.2994506737373,	91.8448903132026,	20},
		{10.3299505722044,	91.4996954013054,	20},
		{10.3603444077456,	91.1815066234776,	20},
		{10.3906416500775,	90.8917269959478,	20},
		{10.4208522024454,	90.6316571034228,	20},
		{10.4509863624351,	90.402479969121,	20},
		{10.4810547778853,	90.2052463505724,	20},
		{10.5110683984653,	90.040861740267,	20},
		{10.5410384234971,	89.910075095382,	20},
		{10.5709762463459,	89.8134685463766,	20},
		{10.6008933965909,	89.7514507349718,	20},
		{10.6308014805248,	89.7242518015416,	20},
		{10.6607121202969,	89.7319193162802,	20},
		{10.6906368934083,	89.774319334245,	20},
		{10.7205872728751,	89.8511384004948,	20},
		{10.7505745693239,	89.9618893463406,	20},
		{10.7806098745233,	90.1059155982558,	20},
		{10.8107040093229,	90.2824043987226,	20},
		{10.8408674742217,	90.4903946965392,	20},
		{10.8711104049273,	90.7287921166062,	20},
		{10.9014425324054,	90.996382434282,	20},
		{10.93187314795,	91.2918466338402,	20},
		{10.9624110738433,	91.6137776801208,	20},
		{10.9930646395608,	91.9606971523932,	20},
		{11.0238416628579,	92.3310698913552,	20},
		{11.054749436854,	92.7233219882784,	20},
		{11.0857947218816,	93.135855082737,	20},
		{11.1169837431023,	93.567063662016,	20},
		{11.148322191929,	94.0153464802848,	20},
		{11.1798152321976,	94.4791208056638,	20},
		{11.2114675101058,	94.9568337245976,	20},
		{11.2432831681928,	95.4469742609682,	20},
		{11.2752658607671,	95.948077722978,	20},
		{11.3074187750189,	96.45874275543,	20},
		{11.3397446507556,	96.9776272101186,	20},
		{11.3722458039032,	97.5034594428276,	20},
		{11.4049241518982,	98.0350439849328,	20},
		{11.4377812377901,	98.5712576756286,	20},
		{11.4708182576568,	99.1110596000664,	20},
		{11.5040360867808,	99.6534873721116,	20},
		{11.5374353054549,	100.197656022173,	20},
		{11.5710162259759,	100.74276156298,	20},
		{11.6047789190664,	101.288079271487,	20},
		{11.6387232392021,	101.832960407254,	20},
		{11.6728488477634,	102.376825683748,	20},
		{11.7071552397134,	102.919175850249,	20},
		{11.7416417648009,	103.459575262392,	20},
		{11.7763076499201,	103.997655357486,	20},
		{11.8111520209893,	104.533113207769,	20},
		{11.8461739230046,	105.06570604586,	20},
		{11.88137233788,	105.595244626065,	20},
		{11.9167462048887,	106.121601026198,	20},
		{11.9522944363037,	106.644694245122,	20},
		{11.9880159333161,	107.16449103719,	20},
		{12.0239096036882,	107.681011116169,	20},
		{12.0599743744581,	108.194312309801,	20},
		{12.0962092054538,	108.70449298715,	20},
		{12.1326131053031,	109.211699547691,	20},
		{12.1691851401697,	109.716104599993,	20},
		{12.2059244491933,	110.217927070641,	20},
		{12.2428302540423,	110.717414547146,	20},
		{12.2799018707876,	111.214850235804,	20},
		{12.3171387199363,	111.710547446168,	20},
		{12.3545403389605,	112.204857072506,	20},
		{12.3921063906972,	112.698155210138,	20},
		{12.429836674718,	113.190852062435,	20},
		{12.4677311375997,	113.683388645176,	20},
		{12.5056165171965,	113.656138790208,	20},
		{12.543229693551,	112.839529063481,	20},
		{12.5803907350536,	111.483124507983,	20},
		{12.616917032582,	109.578892585179,	20},
		{12.6526233902664,	107.119073053082,	20},
		{12.6873221386192,	104.096245058534,	20},
		{12.7208232760608,	100.50341232478,	20},
		{12.7529346390378,	96.3340889309598,	20},
		{12.783462109859,	91.5824124636384,	20},
		{12.8122098656529,	86.243267381661,	20},
		{12.8390706459707,	80.5823409533112,	20},
		{12.8640273538158,	74.8701235352718,	20},
		{12.8870639606807,	69.1098205948956,	20},
		{12.9081655280058,	63.3047019753162,	20},
		{12.9273182347342,	57.4581201852108,	20},
		{12.9445094099608,	51.5735256798248,	20},
		{12.9597275681796,	45.6544746562084,	20},
		{12.9729624464746,	39.7046348851973,	20},
		{12.9842050414112,	33.727784809729,	20},
		{12.9934476447482,	27.7278100108516,	20},
		{13.0008671503579,	22.2585168291154,	20},
		{13.0067339748205,	17.6004733878491,	20},
		{13.0112284546171,	13.4834393899289,	20},
		{13.0145320127114,	9.91067428282218,	20},
		{13.0168268947595,	6.8846461442796,	20},
		{13.0182959399052,	4.40713543698835,	20},
		{13.0191223878595,	2.47934386305352,	20},
		{13.019489723509,	1.10200694853878,	20},
		{13.0195815598688,	0.275509079288701,	20},
		{13.0195815598688,	0,	20},};
}
